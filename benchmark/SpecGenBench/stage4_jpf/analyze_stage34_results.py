#!/usr/bin/env python3
from __future__ import annotations

import argparse
import csv
import json
from collections import Counter
from pathlib import Path


def read_prepare_summary(src_root: Path) -> dict:
    path = src_root / "prepare_summary.json"
    if not path.is_file():
        return {}
    return json.loads(path.read_text(encoding="utf-8"))


def read_compile_summary(path: Path) -> list[dict[str, str]]:
    rows: list[dict[str, str]] = []
    if not path.is_file():
        return rows
    for line in path.read_text(encoding="utf-8", errors="replace").splitlines():
        if not line.strip():
            continue
        parts = line.split("\t")
        row = {
            "rel_path": parts[0] if len(parts) > 0 else "",
            "class_name": parts[1] if len(parts) > 1 else "",
            "status": parts[2] if len(parts) > 2 else "",
            "detail": parts[3] if len(parts) > 3 else "",
        }
        rows.append(row)
    return rows


def read_jpf_rows(path: Path) -> list[dict[str, str]]:
    if not path.is_file():
        return []
    with path.open(newline="", encoding="utf-8") as f:
        return list(csv.DictReader(f))


def pass_kind_from_notes(notes: str) -> str:
    for part in (notes or "").split(";"):
        part = part.strip()
        if part.startswith("pass_kind="):
            return part.split("=", 1)[1]
    return ""


def main() -> int:
    ap = argparse.ArgumentParser(description="Summarize Stage 3/4 compile + JPF results for a staged run.")
    ap.add_argument("--run-root", type=Path, required=True, help="Run root containing src/, rac_javamath/, and jpf/")
    args = ap.parse_args()

    run_root = args.run_root
    src_root = run_root / "src"
    compile_summary_path = run_root / "rac_javamath" / "compile_logs" / "summary.tsv"
    jpf_csv_path = run_root / "jpf" / "results" / "jpf_verification.csv"

    prepare = read_prepare_summary(src_root)
    compile_rows = read_compile_summary(compile_summary_path)
    jpf_rows = read_jpf_rows(jpf_csv_path)

    compile_status = Counter(row.get("status", "") for row in compile_rows)
    jpf_pass = sum(row.get("jpf_pass") == "true" for row in jpf_rows)
    jpf_fail = sum(row.get("jpf_pass") == "false" for row in jpf_rows)
    jpf_timeout = sum(row.get("timeout") == "true" for row in jpf_rows)
    violation_counts = Counter(
        row.get("violation_type", "")
        for row in jpf_rows
        if row.get("jpf_pass") == "false" and row.get("violation_type")
    )
    pass_kind_counts = Counter(
        pass_kind_from_notes(row.get("notes", ""))
        for row in jpf_rows
        if row.get("jpf_pass") == "true"
    )
    if "" in pass_kind_counts:
        del pass_kind_counts[""]

    summary = {
        "run_root": str(run_root),
        "config": run_root.name,
        "prepare": {
            "expected_count": prepare.get("expected_count", 0),
            "staged_count": prepare.get("staged_count", 0),
            "missing_count": prepare.get("missing_count", 0),
            "extra_count": prepare.get("extra_count", 0),
            "missing_methods": prepare.get("missing_methods", []),
        },
        "compile": {
            "summary_path": str(compile_summary_path),
            "total": len(compile_rows),
            "ok": compile_status.get("ok", 0),
            "fail": compile_status.get("fail", 0),
        },
        "jpf": {
            "csv_path": str(jpf_csv_path),
            "total": len(jpf_rows),
            "pass": jpf_pass,
            "fail": jpf_fail,
            "timeout": jpf_timeout,
            "violation_types": dict(sorted(violation_counts.items())),
            "pass_kinds": dict(sorted(pass_kind_counts.items())),
        },
    }

    out_path = run_root / "analysis_summary.json"
    out_path.write_text(json.dumps(summary, indent=2, sort_keys=True) + "\n", encoding="utf-8")

    print(f"Config: {summary['config']}")
    print(
        "Prepared: "
        f"{summary['prepare']['staged_count']}/{summary['prepare']['expected_count']} "
        f"(missing={summary['prepare']['missing_count']}, extra={summary['prepare']['extra_count']})"
    )
    print(
        "Stage 3 compile: "
        f"ok={summary['compile']['ok']} fail={summary['compile']['fail']} total={summary['compile']['total']}"
    )
    print(
        "Stage 4 JPF: "
        f"pass={summary['jpf']['pass']} fail={summary['jpf']['fail']} "
        f"timeout={summary['jpf']['timeout']} total={summary['jpf']['total']}"
    )
    if summary["jpf"]["pass_kinds"]:
        print("Pass kinds:", json.dumps(summary["jpf"]["pass_kinds"], sort_keys=True))
    if summary["jpf"]["violation_types"]:
        print("Violation types:", json.dumps(summary["jpf"]["violation_types"], sort_keys=True))
    print(f"Analysis JSON: {out_path}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
