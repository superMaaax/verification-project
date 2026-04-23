#!/usr/bin/env python3
from __future__ import annotations

import argparse
import csv
import json
from pathlib import Path


def parse_config(config: str) -> tuple[str, str]:
    parts = config.split("__", 1)
    if len(parts) != 2:
        return config, ""
    return parts[0], parts[1]


def load_run_summary(run_root: Path) -> dict | None:
    path = run_root / "analysis_summary.json"
    if not path.is_file():
        return None
    data = json.loads(path.read_text(encoding="utf-8"))
    strategy, model = parse_config(data.get("config", run_root.name))
    data["strategy"] = strategy
    data["model"] = model
    return data


def summary_row(data: dict) -> dict[str, object]:
    prepare = data.get("prepare", {})
    compile_ = data.get("compile", {})
    jpf = data.get("jpf", {})
    total = int(jpf.get("total", 0) or 0)
    passed = int(jpf.get("pass", 0) or 0)
    failed = int(jpf.get("fail", 0) or 0)
    timeout = int(jpf.get("timeout", 0) or 0)
    return {
        "config": data.get("config", ""),
        "strategy": data.get("strategy", ""),
        "model": data.get("model", ""),
        "prepare_expected": int(prepare.get("expected_count", 0) or 0),
        "prepare_staged": int(prepare.get("staged_count", 0) or 0),
        "prepare_missing": int(prepare.get("missing_count", 0) or 0),
        "compile_total": int(compile_.get("total", 0) or 0),
        "compile_ok": int(compile_.get("ok", 0) or 0),
        "compile_fail": int(compile_.get("fail", 0) or 0),
        "jpf_total": total,
        "jpf_pass": passed,
        "jpf_fail": failed,
        "jpf_timeout": timeout,
        "jpf_pass_rate": f"{(passed / total):.4f}" if total else "",
        "jpf_fail_rate": f"{(failed / total):.4f}" if total else "",
        "jpf_timeout_rate": f"{(timeout / total):.4f}" if total else "",
    }


def pairwise_row(group_key: str, left: dict, right: dict, axis_name: str, left_label: str, right_label: str) -> dict[str, object]:
    left_row = summary_row(left)
    right_row = summary_row(right)

    def delta(name: str) -> int:
        return int(right_row[name]) - int(left_row[name])

    left_total = int(left_row["jpf_total"])
    right_total = int(right_row["jpf_total"])
    left_pass = int(left_row["jpf_pass"])
    right_pass = int(right_row["jpf_pass"])
    left_timeout = int(left_row["jpf_timeout"])
    right_timeout = int(right_row["jpf_timeout"])

    return {
        "group": group_key,
        axis_name: f"{left_label}_to_{right_label}",
        "left_config": left_row["config"],
        "right_config": right_row["config"],
        "left_label": left_label,
        "right_label": right_label,
        "prepare_staged_delta": delta("prepare_staged"),
        "prepare_missing_delta": delta("prepare_missing"),
        "compile_ok_delta": delta("compile_ok"),
        "compile_fail_delta": delta("compile_fail"),
        "jpf_pass_delta": delta("jpf_pass"),
        "jpf_fail_delta": delta("jpf_fail"),
        "jpf_timeout_delta": delta("jpf_timeout"),
        "jpf_pass_rate_delta": f"{((right_pass / right_total) if right_total else 0.0) - ((left_pass / left_total) if left_total else 0.0):.4f}",
        "jpf_timeout_rate_delta": f"{((right_timeout / right_total) if right_total else 0.0) - ((left_timeout / left_total) if left_total else 0.0):.4f}",
    }


def write_csv(path: Path, rows: list[dict[str, object]], fieldnames: list[str]) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    with path.open("w", newline="", encoding="utf-8") as f:
        writer = csv.DictWriter(f, fieldnames=fieldnames)
        writer.writeheader()
        for row in rows:
            writer.writerow(row)


def main() -> int:
    ap = argparse.ArgumentParser(description="Compare Stage 3/4 analysis summaries across strategies and models.")
    ap.add_argument("--work-root", type=Path, required=True, help="Root containing per-config run directories.")
    ap.add_argument("--out-dir", type=Path, default=None, help="Directory for comparison CSV/JSON outputs.")
    args = ap.parse_args()

    work_root = args.work_root
    out_dir = args.out_dir or (work_root / "comparisons")

    runs: list[dict] = []
    for child in sorted(work_root.iterdir()) if work_root.is_dir() else []:
        if not child.is_dir():
            continue
        data = load_run_summary(child)
        if data is not None:
            runs.append(data)

    if not runs:
        raise FileNotFoundError(f"No analysis_summary.json files found under {work_root}")

    runs.sort(key=lambda item: (str(item.get("strategy", "")), str(item.get("model", ""))))

    summary_rows = [summary_row(run) for run in runs]
    summary_fields = [
        "config",
        "strategy",
        "model",
        "prepare_expected",
        "prepare_staged",
        "prepare_missing",
        "compile_total",
        "compile_ok",
        "compile_fail",
        "jpf_total",
        "jpf_pass",
        "jpf_fail",
        "jpf_timeout",
        "jpf_pass_rate",
        "jpf_fail_rate",
        "jpf_timeout_rate",
    ]
    write_csv(out_dir / "summary.csv", summary_rows, summary_fields)

    by_model: dict[str, dict[str, dict]] = {}
    by_strategy: dict[str, dict[str, dict]] = {}
    for run in runs:
        by_model.setdefault(run["model"], {})[run["strategy"]] = run
        by_strategy.setdefault(run["strategy"], {})[run["model"]] = run

    strategy_compare_rows: list[dict[str, object]] = []
    for model, grouped in sorted(by_model.items()):
        if "zero_shot" in grouped and "few_shot" in grouped:
            strategy_compare_rows.append(
                pairwise_row(
                    model,
                    grouped["zero_shot"],
                    grouped["few_shot"],
                    "comparison_axis",
                    "zero_shot",
                    "few_shot",
                )
            )

    model_compare_rows: list[dict[str, object]] = []
    preferred_left = "Qwen_Qwen3-Coder-Next-FP8"
    preferred_right = "MiniMaxAI_MiniMax-M2.7"
    for strategy, grouped in sorted(by_strategy.items()):
        if preferred_left in grouped and preferred_right in grouped:
            left = grouped[preferred_left]
            right = grouped[preferred_right]
        elif len(grouped) >= 2:
            model_names = sorted(grouped)
            left = grouped[model_names[0]]
            right = grouped[model_names[1]]
        else:
            continue
        model_compare_rows.append(
            pairwise_row(
                strategy,
                left,
                right,
                "comparison_axis",
                str(left["model"]),
                str(right["model"]),
            )
        )

    compare_fields = [
        "group",
        "comparison_axis",
        "left_config",
        "right_config",
        "left_label",
        "right_label",
        "prepare_staged_delta",
        "prepare_missing_delta",
        "compile_ok_delta",
        "compile_fail_delta",
        "jpf_pass_delta",
        "jpf_fail_delta",
        "jpf_timeout_delta",
        "jpf_pass_rate_delta",
        "jpf_timeout_rate_delta",
    ]
    write_csv(out_dir / "compare_by_strategy.csv", strategy_compare_rows, compare_fields)
    write_csv(out_dir / "compare_by_model.csv", model_compare_rows, compare_fields)

    comparison_json = {
        "work_root": str(work_root),
        "runs_found": len(runs),
        "summary_csv": str(out_dir / "summary.csv"),
        "compare_by_strategy_csv": str(out_dir / "compare_by_strategy.csv"),
        "compare_by_model_csv": str(out_dir / "compare_by_model.csv"),
        "configs": [row["config"] for row in summary_rows],
    }
    (out_dir / "comparison_summary.json").write_text(
        json.dumps(comparison_json, indent=2, sort_keys=True) + "\n",
        encoding="utf-8",
    )

    print(f"Runs found: {len(runs)}")
    print(f"Summary CSV: {out_dir / 'summary.csv'}")
    print(f"Strategy comparison CSV: {out_dir / 'compare_by_strategy.csv'}")
    print(f"Model comparison CSV: {out_dir / 'compare_by_model.csv'}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
