#!/usr/bin/env python3
from __future__ import annotations

import argparse
import json
import shutil
from dataclasses import dataclass
from pathlib import Path


@dataclass(frozen=True)
class Benchmark:
    name: str
    source_path: Path


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


def main() -> int:
    ap = argparse.ArgumentParser(description="Stage SpecGenBench stage-2 flat outputs into a Stage 3/4 source tree.")
    ap.add_argument("--flat-dir", type=Path, required=True, help="Flat output dir like experiment/.../outputs/few_shot")
    ap.add_argument("--strategy", required=True, help="Strategy token used in filenames, e.g. few_shot")
    ap.add_argument("--model", required=True, help="Safe model token used in filenames, e.g. MiniMaxAI_MiniMax-M2.7")
    ap.add_argument("--benchmark-root", type=Path, required=True, help="Benchmark root like benchmark/SpecGenBench/common")
    ap.add_argument("--out", type=Path, required=True, help="Output tree with Method/Method.java layout")
    args = ap.parse_args()

    if not args.flat_dir.is_dir():
        raise FileNotFoundError(f"Flat output dir not found: {args.flat_dir}")
    if not args.benchmark_root.is_dir():
        raise FileNotFoundError(f"Benchmark root not found: {args.benchmark_root}")

    expected = discover_benchmarks(args.benchmark_root)
    expected_names = {benchmark.name for benchmark in expected}
    suffix = f"__{args.strategy}__{args.model}__code.java"

    matched: dict[str, Path] = {}
    for path in sorted(args.flat_dir.glob(f"*{suffix}")):
        method = path.name[: -len(suffix)]
        matched[method] = path

    args.out.mkdir(parents=True, exist_ok=True)

    staged = 0
    staged_names: list[str] = []
    for method in sorted(expected_names):
        source = matched.get(method)
        if source is None:
            continue
        target_dir = args.out / method
        target_dir.mkdir(parents=True, exist_ok=True)
        shutil.copyfile(source, target_dir / f"{method}.java")
        staged += 1
        staged_names.append(method)

    missing = sorted(expected_names - set(staged_names))
    extras = sorted(set(matched) - expected_names)
    summary = {
        "flat_dir": str(args.flat_dir),
        "strategy": args.strategy,
        "model": args.model,
        "benchmark_root": str(args.benchmark_root),
        "output_root": str(args.out),
        "expected_count": len(expected_names),
        "matched_flat_files": len(matched),
        "staged_count": staged,
        "missing_count": len(missing),
        "extra_count": len(extras),
        "missing_methods": missing,
        "extra_methods": extras,
    }

    (args.out / "prepare_summary.json").write_text(json.dumps(summary, indent=2, sort_keys=True) + "\n", encoding="utf-8")

    print(json.dumps(summary, indent=2, sort_keys=True))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
