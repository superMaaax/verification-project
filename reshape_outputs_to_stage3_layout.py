"""
Reshape Stage 2 LLM outputs into the directory layout that Zhong's Stage 3
compile script expects.

Stage 2 emits flat filenames:
    <output_dir>/outputs/<strategy>/<Method>__<strategy>__<safe_model>__code.java

Zhong's Stage 3 (compile_common_<strategy>_jml_rac_javamath.sh) reads from:
    benchmark/SpecGenBench/common_<strategy>_jml/<Method>/<Method>.java

This script copies the extracted Java sources into that nested layout.
By default it operates on a single strategy at a time.
"""

from __future__ import annotations

import argparse
import re
import shutil
from pathlib import Path


CODE_SUFFIX_RE = re.compile(r"__(zero_shot|few_shot|cot)__.+__code\.java$")


def discover_code_files(src_dir: Path) -> list[tuple[str, Path]]:
    pairs: list[tuple[str, Path]] = []
    for path in sorted(src_dir.glob("*__code.java")):
        m = CODE_SUFFIX_RE.search(path.name)
        if not m:
            continue
        method = path.name[: m.start()]
        pairs.append((method, path))
    return pairs


def reshape(src_dir: Path, dst_dir: Path, *, force: bool) -> int:
    pairs = discover_code_files(src_dir)
    if not pairs:
        raise SystemExit(f"No *__code.java files found under {src_dir}")

    if dst_dir.exists():
        if not force:
            raise SystemExit(
                f"Destination already exists: {dst_dir}\n"
                f"Pass --force to overwrite."
            )
        shutil.rmtree(dst_dir)

    dst_dir.mkdir(parents=True)
    for method, src in pairs:
        method_dir = dst_dir / method
        method_dir.mkdir(parents=True, exist_ok=True)
        shutil.copyfile(src, method_dir / f"{method}.java")

    print(f"Reshaped {len(pairs)} methods")
    print(f"  src: {src_dir}")
    print(f"  dst: {dst_dir}")
    return len(pairs)


def main() -> int:
    parser = argparse.ArgumentParser(
        description="Reshape Stage 2 outputs into Stage 3 nested layout."
    )
    parser.add_argument(
        "--src",
        required=True,
        help="Stage 2 outputs dir, e.g. experiment/specgenbench-openrouter/outputs/zero_shot",
    )
    parser.add_argument(
        "--dst",
        required=True,
        help="Stage 3 input dir, e.g. benchmark/SpecGenBench/common_zero_shot_jml",
    )
    parser.add_argument(
        "--force",
        action="store_true",
        help="Remove --dst if it already exists",
    )
    args = parser.parse_args()
    reshape(Path(args.src), Path(args.dst), force=args.force)
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
