#!/usr/bin/env python3
from __future__ import annotations

import argparse
import csv
from pathlib import Path


BASE_FIELDS = [
    "method",
    "jpf_pass",
    "violation_type",
    "counterexample",
    "instructions",
    "states_new",
    "states_end",
    "depth",
    "timeout",
    "notes",
]


def parse_config(name: str) -> tuple[str, str]:
    parts = name.split("__", 1)
    if len(parts) != 2:
        return name, ""
    return parts[0], parts[1]


def benchmark_from_method(method: str) -> str:
    if "." in method:
        return method.split(".", 1)[0]
    return method


def pass_kind_from_notes(notes: str) -> str:
    for part in (notes or "").split(";"):
        part = part.strip()
        if part.startswith("pass_kind="):
            return part.split("=", 1)[1]
    return ""


def load_run_rows(work_root: Path) -> list[dict[str, str]]:
    rows: list[dict[str, str]] = []
    for run_dir in sorted(work_root.iterdir()):
        if not run_dir.is_dir():
            continue
        csv_path = run_dir / "jpf" / "results" / "jpf_verification.csv"
        if not csv_path.is_file():
            continue
        strategy, model = parse_config(run_dir.name)
        with csv_path.open(newline="", encoding="utf-8") as f:
            reader = csv.DictReader(f)
            for row in reader:
                method = row.get("method", "")
                merged = dict(row)
                merged["config"] = run_dir.name
                merged["strategy"] = strategy
                merged["model"] = model
                merged["benchmark"] = benchmark_from_method(method)
                merged["pass_kind"] = pass_kind_from_notes(row.get("notes", ""))
                rows.append(merged)
    return rows


def write_csv(path: Path, fieldnames: list[str], rows: list[dict[str, object]]) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    with path.open("w", newline="", encoding="utf-8") as f:
        writer = csv.DictWriter(f, fieldnames=fieldnames)
        writer.writeheader()
        for row in rows:
            writer.writerow(row)


def build_wide_rows(rows: list[dict[str, str]]) -> tuple[list[str], list[dict[str, object]]]:
    configs = sorted({row["config"] for row in rows})
    by_benchmark: dict[str, dict[str, dict[str, str]]] = {}
    for row in rows:
        by_benchmark.setdefault(row["benchmark"], {})[row["config"]] = row

    fieldnames = ["benchmark"]
    suffixes = [
        "method",
        "jpf_pass",
        "violation_type",
        "timeout",
        "pass_kind",
        "instructions",
        "states_new",
        "states_end",
        "depth",
        "notes",
    ]
    for config in configs:
        for suffix in suffixes:
            fieldnames.append(f"{config}__{suffix}")

    wide_rows: list[dict[str, object]] = []
    for benchmark in sorted(by_benchmark):
        out: dict[str, object] = {"benchmark": benchmark}
        for config in configs:
            row = by_benchmark[benchmark].get(config, {})
            out[f"{config}__method"] = row.get("method", "")
            out[f"{config}__jpf_pass"] = row.get("jpf_pass", "")
            out[f"{config}__violation_type"] = row.get("violation_type", "")
            out[f"{config}__timeout"] = row.get("timeout", "")
            out[f"{config}__pass_kind"] = row.get("pass_kind", "")
            out[f"{config}__instructions"] = row.get("instructions", "")
            out[f"{config}__states_new"] = row.get("states_new", "")
            out[f"{config}__states_end"] = row.get("states_end", "")
            out[f"{config}__depth"] = row.get("depth", "")
            out[f"{config}__notes"] = row.get("notes", "")
        wide_rows.append(out)
    return fieldnames, wide_rows


def main() -> int:
    ap = argparse.ArgumentParser(description="Export per-method Stage 4 results across multiple run configs.")
    ap.add_argument("--work-root", type=Path, required=True, help="Root containing per-config run directories.")
    ap.add_argument("--out-dir", type=Path, default=None, help="Directory for exported per-method CSV files.")
    args = ap.parse_args()

    work_root = args.work_root
    out_dir = args.out_dir or (work_root / "comparisons")
    rows = load_run_rows(work_root)
    if not rows:
        raise FileNotFoundError(f"No jpf_verification.csv files found under {work_root}")

    long_fieldnames = ["benchmark", "config", "strategy", "model", "pass_kind"] + BASE_FIELDS
    long_rows = []
    for row in sorted(rows, key=lambda item: (item["benchmark"], item["strategy"], item["model"])):
        out = {field: row.get(field, "") for field in long_fieldnames}
        long_rows.append(out)
    write_csv(out_dir / "per_method_long.csv", long_fieldnames, long_rows)

    wide_fieldnames, wide_rows = build_wide_rows(rows)
    write_csv(out_dir / "per_method_wide.csv", wide_fieldnames, wide_rows)

    print(f"Per-method long CSV: {out_dir / 'per_method_long.csv'}")
    print(f"Per-method wide CSV: {out_dir / 'per_method_wide.csv'}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
