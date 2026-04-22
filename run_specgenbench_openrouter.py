"""
Batch JML generation for SpecGenBench using OpenRouter.

OpenRouter exposes an OpenAI-compatible chat-completions API, so this script
uses the official `openai` SDK with a custom `base_url`.

Adapted from `run_specgenbench_together.py` (Minchao's Together AI version).
The differences are:

  - SDK: `openai.AsyncOpenAI` instead of `together.AsyncTogether`
  - base_url: https://openrouter.ai/api/v1
  - default model: openai/gpt-5.4-mini
  - env var: OPENROUTER_API_KEY (auto-loaded from .env if python-dotenv present)
  - default output dir: experiment/specgenbench-openrouter
  - dropped Together-specific `chat_template_kwargs`
"""

from __future__ import annotations

import argparse
import asyncio
import json
import os
import random
import re
import time
from dataclasses import dataclass
from pathlib import Path
from typing import Any

try:
    from dotenv import load_dotenv
    load_dotenv(Path(__file__).resolve().parent / ".env")
except ImportError:
    pass

from openai import AsyncOpenAI


DEFAULT_MODEL = "openai/gpt-5.4-mini"
DEFAULT_BASE_URL = "https://openrouter.ai/api/v1"
DEFAULT_BENCHMARK_DIR = "benchmark/SpecGenBench/common"
DEFAULT_PROMPT_DIR = "experiment/pilot-5/prompts"
DEFAULT_OUTPUT_DIR = "experiment/specgenbench-openrouter"

SYSTEM_PROMPT = (
    "You are a JML specification generator for Java programs. "
    "Insert JML comments into the provided Java source without changing "
    "executable Java code. Output only the complete annotated Java file."
)

CODE_FENCE_RE = re.compile(r"```(?:java)?\s*(.*?)```", re.IGNORECASE | re.DOTALL)


@dataclass(frozen=True)
class Benchmark:
    name: str
    source_path: Path


def safe_name(value: str) -> str:
    return re.sub(r"[^A-Za-z0-9_.-]+", "_", value).strip("_")


def discover_benchmarks(benchmark_dir: Path) -> list[Benchmark]:
    benchmarks: list[Benchmark] = []
    for child in sorted(benchmark_dir.iterdir()):
        if not child.is_dir():
            continue

        primary = child / f"{child.name}.java"
        if primary.is_file():
            benchmarks.append(Benchmark(child.name, primary))
            continue

        java_files = sorted(path for path in child.glob("*.java") if not path.name.endswith("Driver.java"))
        if len(java_files) == 1:
            benchmarks.append(Benchmark(child.name, java_files[0]))

    return benchmarks


def render_prompt(template: str, java_source: str) -> str:
    if "{{JAVA_SOURCE}}" not in template:
        raise ValueError("Prompt template must contain {{JAVA_SOURCE}}")
    return template.replace("{{JAVA_SOURCE}}", java_source.rstrip())


def extract_java_code(raw: str) -> str:
    fenced = CODE_FENCE_RE.search(raw)
    if fenced:
        return fenced.group(1).strip() + "\n"

    for marker in ("public class ", "class ", "interface ", "enum "):
        idx = raw.find(marker)
        if idx != -1:
            return raw[idx:].strip() + "\n"

    return raw.strip() + "\n"


def write_text(path: Path, content: str) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(content)


def append_jsonl(path: Path, row: dict[str, Any]) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    with path.open("a") as f:
        f.write(json.dumps(row, sort_keys=True) + "\n")


def jsonable(value: Any) -> Any:
    if value is None or isinstance(value, (str, int, float, bool)):
        return value
    if isinstance(value, list):
        return [jsonable(item) for item in value]
    if isinstance(value, tuple):
        return [jsonable(item) for item in value]
    if isinstance(value, dict):
        return {str(key): jsonable(item) for key, item in value.items()}
    if hasattr(value, "model_dump"):
        return jsonable(value.model_dump())
    if hasattr(value, "dict"):
        return jsonable(value.dict())
    return str(value)


def existing_done(code_path: Path, raw_path: Path) -> bool:
    return code_path.is_file() and raw_path.is_file() and code_path.stat().st_size > 0


async def call_openrouter(
    client: AsyncOpenAI,
    model: str,
    prompt: str,
    temperature: float,
    max_tokens: int,
    retries: int,
    retry_base_delay: float,
) -> tuple[str, dict[str, Any]]:
    kwargs: dict[str, Any] = {
        "model": model,
        "messages": [
            {"role": "system", "content": SYSTEM_PROMPT},
            {"role": "user", "content": prompt},
        ],
        "temperature": temperature,
        "max_tokens": max_tokens,
    }

    last_error: Exception | None = None
    for attempt in range(retries + 1):
        try:
            response = await client.chat.completions.create(**kwargs)
            choice = response.choices[0]
            message = choice.message
            content = message.content or ""
            reasoning = getattr(message, "reasoning", None) or ""
            raw = content if content.strip() else reasoning
            metadata = {
                "finish_reason": jsonable(getattr(choice, "finish_reason", None)),
                "usage": jsonable(getattr(response, "usage", None)),
                "had_reasoning_field": bool(reasoning),
            }
            return raw, metadata
        except Exception as exc:
            last_error = exc
            error_text = str(exc).lower()
            if attempt >= retries:
                break
            is_rate_limit = "429" in error_text or "rate" in error_text or "timeout" in error_text
            wait = retry_base_delay * (2**attempt)
            if not is_rate_limit:
                wait = min(wait, retry_base_delay)
            await asyncio.sleep(wait)

    assert last_error is not None
    raise last_error


async def generate_one(
    client: AsyncOpenAI | None,
    benchmark: Benchmark,
    strategy: str,
    model: str,
    prompt_template: str,
    output_dir: Path,
    semaphore: asyncio.Semaphore,
    args: argparse.Namespace,
) -> dict[str, Any]:
    safe_model = safe_name(model)
    stem = f"{benchmark.name}__{strategy}__{safe_model}"
    prompt_path = output_dir / "prompts_rendered" / strategy / f"{stem}__prompt.txt"
    raw_path = output_dir / "outputs" / strategy / f"{stem}__raw.txt"
    code_path = output_dir / "outputs" / strategy / f"{stem}__code.java"

    if not args.overwrite and existing_done(code_path, raw_path):
        return {
            "method": benchmark.name,
            "status": "skipped_existing",
            "source_path": str(benchmark.source_path),
            "prompt_path": str(prompt_path),
            "raw_path": str(raw_path),
            "code_path": str(code_path),
        }

    source = benchmark.source_path.read_text()
    prompt = render_prompt(prompt_template, source)
    write_text(prompt_path, prompt)

    if args.dry_run:
        return {
            "method": benchmark.name,
            "status": "dry_run",
            "source_path": str(benchmark.source_path),
            "prompt_path": str(prompt_path),
        }

    assert client is not None
    async with semaphore:
        if args.delay > 0:
            await asyncio.sleep(args.delay)
        started = time.time()
        try:
            raw, api_metadata = await call_openrouter(
                client=client,
                model=model,
                prompt=prompt,
                temperature=args.temperature,
                max_tokens=args.max_tokens,
                retries=args.retries,
                retry_base_delay=args.retry_base_delay,
            )
            elapsed = time.time() - started
            code = extract_java_code(raw)
            write_text(raw_path, raw)
            write_text(code_path, code)
            return {
                "method": benchmark.name,
                "status": "ok",
                "source_path": str(benchmark.source_path),
                "prompt_path": str(prompt_path),
                "raw_path": str(raw_path),
                "code_path": str(code_path),
                "elapsed_seconds": round(elapsed, 3),
                "api": api_metadata,
            }
        except Exception as exc:
            elapsed = time.time() - started
            return {
                "method": benchmark.name,
                "status": "error",
                "source_path": str(benchmark.source_path),
                "prompt_path": str(prompt_path),
                "elapsed_seconds": round(elapsed, 3),
                "error": str(exc),
            }


def select_benchmarks(benchmarks: list[Benchmark], args: argparse.Namespace) -> list[Benchmark]:
    selected = benchmarks
    if args.methods:
        wanted = set(args.methods)
        selected = [benchmark for benchmark in selected if benchmark.name in wanted]
        missing = sorted(wanted - {benchmark.name for benchmark in selected})
        if missing:
            raise ValueError(f"Unknown benchmark method(s): {', '.join(missing)}")

    if args.seed is not None:
        random.seed(args.seed)

    if args.shuffle:
        selected = selected[:]
        random.shuffle(selected)

    if args.max_methods is not None:
        selected = selected[: args.max_methods]

    return selected


async def main_async(args: argparse.Namespace) -> int:
    benchmark_dir = Path(args.benchmark_dir)
    prompt_path = Path(args.prompt_dir) / f"{args.strategy}.txt"
    output_dir = Path(args.output_dir)

    if not benchmark_dir.is_dir():
        raise FileNotFoundError(f"Benchmark directory not found: {benchmark_dir}")
    if not prompt_path.is_file():
        raise FileNotFoundError(f"Prompt template not found: {prompt_path}")

    prompt_template = prompt_path.read_text()
    benchmarks = select_benchmarks(discover_benchmarks(benchmark_dir), args)
    if not benchmarks:
        raise ValueError("No benchmarks selected")

    safe_model = safe_name(args.model)
    metadata_path = output_dir / "results" / f"{args.strategy}__{safe_model}__metadata.jsonl"
    run_config_path = output_dir / "results" / f"{args.strategy}__{safe_model}__run_config.json"
    write_text(
        run_config_path,
        json.dumps(
            {
                "model": args.model,
                "strategy": args.strategy,
                "benchmark_dir": str(benchmark_dir),
                "prompt_path": str(prompt_path),
                "output_dir": str(output_dir),
                "base_url": args.base_url,
                "temperature": args.temperature,
                "max_tokens": args.max_tokens,
                "concurrency": args.concurrency,
                "num_selected": len(benchmarks),
                "dry_run": args.dry_run,
            },
            indent=2,
            sort_keys=True,
        )
        + "\n",
    )

    print(f"Model: {args.model}")
    print(f"Base URL: {args.base_url}")
    print(f"Strategy: {args.strategy}")
    print(f"Benchmarks selected: {len(benchmarks)}")
    print(f"Metadata: {metadata_path}")
    if args.dry_run:
        print("Dry run: rendering prompts only; no OpenRouter API calls.")

    api_key = args.api_key or os.environ.get("OPENROUTER_API_KEY")
    if args.api_key_file:
        api_key = Path(args.api_key_file).read_text().strip()
    if not args.dry_run and not api_key:
        raise RuntimeError(
            "OPENROUTER_API_KEY not set. Put it in .env, export it, or pass --api-key."
        )

    client = None
    if not args.dry_run:
        client = AsyncOpenAI(api_key=api_key, base_url=args.base_url)

    semaphore = asyncio.Semaphore(args.concurrency)
    tasks = [
        asyncio.create_task(
            generate_one(
                client=client,
                benchmark=benchmark,
                strategy=args.strategy,
                model=args.model,
                prompt_template=prompt_template,
                output_dir=output_dir,
                semaphore=semaphore,
                args=args,
            )
        )
        for benchmark in benchmarks
    ]

    counts: dict[str, int] = {}
    for idx, task in enumerate(asyncio.as_completed(tasks), start=1):
        row = await task
        row["model"] = args.model
        row["strategy"] = args.strategy
        row["completed_at"] = time.strftime("%Y-%m-%dT%H:%M:%S%z")
        append_jsonl(metadata_path, row)
        counts[row["status"]] = counts.get(row["status"], 0) + 1
        print(f"[{idx}/{len(tasks)}] {row['method']}: {row['status']}")

    print("Summary:", ", ".join(f"{key}={value}" for key, value in sorted(counts.items())))
    return 1 if counts.get("error") else 0


def build_parser() -> argparse.ArgumentParser:
    parser = argparse.ArgumentParser(description="Generate JML specs for SpecGenBench with OpenRouter.")
    parser.add_argument("--model", default=os.environ.get("OPENROUTER_MODEL", DEFAULT_MODEL))
    parser.add_argument("--base-url", default=os.environ.get("OPENROUTER_BASE_URL", DEFAULT_BASE_URL))
    parser.add_argument("--strategy", choices=["zero_shot", "few_shot", "cot"], default="zero_shot")
    parser.add_argument("--benchmark-dir", default=DEFAULT_BENCHMARK_DIR)
    parser.add_argument("--prompt-dir", default=DEFAULT_PROMPT_DIR)
    parser.add_argument("--output-dir", default=DEFAULT_OUTPUT_DIR)
    parser.add_argument("--methods", nargs="+", help="Optional benchmark method names, e.g. FindFirstZero Absolute")
    parser.add_argument("--max-methods", type=int, default=None, help="Limit selected methods for testing")
    parser.add_argument("--shuffle", action="store_true", help="Shuffle selected benchmarks before applying --max-methods")
    parser.add_argument("--seed", type=int, default=None)
    parser.add_argument("--concurrency", type=int, default=2)
    parser.add_argument("--delay", type=float, default=0.0, help="Seconds to wait before each API call")
    parser.add_argument("--temperature", type=float, default=0.0)
    parser.add_argument("--max-tokens", type=int, default=4096)
    parser.add_argument("--retries", type=int, default=4)
    parser.add_argument("--retry-base-delay", type=float, default=2.0)
    parser.add_argument("--api-key", default=None, help="OpenRouter API key; defaults to OPENROUTER_API_KEY env or .env")
    parser.add_argument("--api-key-file", default=None, help="Path to a file containing the OpenRouter API key")
    parser.add_argument("--overwrite", action="store_true", help="Regenerate files even if raw/code outputs already exist")
    parser.add_argument("--dry-run", action="store_true", help="Render prompts and metadata without API calls")
    return parser


def main() -> int:
    parser = build_parser()
    args = parser.parse_args()
    return asyncio.run(main_async(args))


if __name__ == "__main__":
    raise SystemExit(main())
