#!/usr/bin/env bash
set -euo pipefail

ROOT="$(CDPATH= cd -- "$(dirname -- "$0")/../.." && pwd)"
OUTPUTS_ROOT="$ROOT/experiment/specgenbench-together/outputs"
BENCHMARK_ROOT="$ROOT/benchmark/SpecGenBench/common"
WORK_ROOT="$ROOT/experiment/specgenbench-together/stage34_runs"
JPF_ROOT="${JPF_ROOT:-$ROOT/.tools/jpf-core}"
OPENJML_ROOT="${OPENJML_ROOT:-$ROOT/.tools/openjml}"
OPENJML_JAVA_BASE="${OPENJML_JAVA_BASE:-$OPENJML_ROOT/NO_JAVA_BASE}"
WALL_TIMEOUT="30"
JAVA11_HOME=""
PATCH_JMLRUNTIME=0
MAX_METHODS=""
STRATEGIES=("zero_shot" "few_shot")
MODELS=()
SKIP_COMPARE=0
OPENJML_BIN="${OPENJML:-}"
JML_RUNTIME="${JMLRT:-}"

usage() {
  cat <<EOF
Usage:
  bash benchmark/SpecGenBench/run_stage3_stage4_from_experiment_outputs.sh [options]

Options:
  --outputs-root PATH       Stage-2 flat outputs root. Default: experiment/specgenbench-together/outputs
  --benchmark-root PATH     SpecGenBench common benchmarks. Default: benchmark/SpecGenBench/common
  --work-root PATH          Root for staged Stage 3/4 runs. Default: experiment/specgenbench-together/stage34_runs
  --strategy NAME           Strategy to run. Repeatable. Default: zero_shot and few_shot
  --model SAFE_NAME         Safe model token from filenames. Repeatable. Default: auto-detect
  --wall-timeout SECONDS    Stage 4 timeout per benchmark. Default: 30
  --java11-home PATH        JDK 11 home for Stage 4 harness/JPF runs
  --openjml-root PATH       Directory containing openjml and jmlruntime.jar
  --openjml PATH            OpenJML executable path
  --jml-runtime PATH        jmlruntime.jar (or jmlruntime_java11.jar for Stage 4)
  --jpf-root PATH           JPF root containing build/RunJPF.jar
  --openjml-java-base PATH  OpenJML java.base modules path for JPF
  --patch-jmlruntime        Build .tools/openjml/jmlruntime_java11.jar if it is missing
  --max-methods N           Forwarded to Stage 4 for debugging smaller batches
  --skip-compare            Do not generate cross-run comparison CSVs at the end
  --help                    Show this help

Examples:
  bash benchmark/SpecGenBench/run_stage3_stage4_from_experiment_outputs.sh \\
    --strategy few_shot \\
    --model MiniMaxAI_MiniMax-M2.7

  bash benchmark/SpecGenBench/run_stage3_stage4_from_experiment_outputs.sh \\
    --model Qwen_Qwen3-Coder-Next-FP8 \\
    --model MiniMaxAI_MiniMax-M2.7
EOF
}

while [[ $# -gt 0 ]]; do
  case "$1" in
    --outputs-root)
      OUTPUTS_ROOT="$2"
      shift 2
      ;;
    --benchmark-root)
      BENCHMARK_ROOT="$2"
      shift 2
      ;;
    --work-root)
      WORK_ROOT="$2"
      shift 2
      ;;
    --strategy)
      if [[ ${#STRATEGIES[@]} -eq 2 && "${STRATEGIES[0]}" == "zero_shot" && "${STRATEGIES[1]}" == "few_shot" ]]; then
        STRATEGIES=()
      fi
      STRATEGIES+=("$2")
      shift 2
      ;;
    --model)
      MODELS+=("$2")
      shift 2
      ;;
    --wall-timeout)
      WALL_TIMEOUT="$2"
      shift 2
      ;;
    --java11-home)
      JAVA11_HOME="$2"
      shift 2
      ;;
    --openjml-root)
      OPENJML_ROOT="$2"
      shift 2
      ;;
    --openjml)
      OPENJML_BIN="$2"
      shift 2
      ;;
    --jml-runtime)
      JML_RUNTIME="$2"
      shift 2
      ;;
    --jpf-root)
      JPF_ROOT="$2"
      shift 2
      ;;
    --openjml-java-base)
      OPENJML_JAVA_BASE="$2"
      shift 2
      ;;
    --patch-jmlruntime)
      PATCH_JMLRUNTIME=1
      shift
      ;;
    --max-methods)
      MAX_METHODS="$2"
      shift 2
      ;;
    --skip-compare)
      SKIP_COMPARE=1
      shift
      ;;
    --help|-h)
      usage
      exit 0
      ;;
    *)
      echo "error: unknown argument: $1" >&2
      usage >&2
      exit 1
      ;;
  esac
done

if [[ ! -d "$OUTPUTS_ROOT" ]]; then
  echo "error: outputs root not found: $OUTPUTS_ROOT" >&2
  exit 1
fi

if [[ ${#MODELS[@]} -eq 0 ]]; then
  mapfile -t MODELS < <(
    python3 - "$OUTPUTS_ROOT" "${STRATEGIES[@]}" <<'PY'
import sys
from collections import Counter
from pathlib import Path

outputs_root = Path(sys.argv[1])
strategies = sys.argv[2:]
counts = Counter()
for strategy in strategies:
    flat_dir = outputs_root / strategy
    if not flat_dir.is_dir():
        continue
    for path in flat_dir.glob("*__code.java"):
        stem = path.name[:-len("__code.java")]
        try:
            _method, strategy_token, model_token = stem.split("__", 2)
        except ValueError:
            continue
        if strategy_token != strategy:
            continue
        counts[model_token] += 1

for model, count in sorted(counts.items()):
    if count >= 5:
        print(model)
PY
  )
fi

if [[ ${#MODELS[@]} -eq 0 ]]; then
  echo "error: no models detected under $OUTPUTS_ROOT" >&2
  exit 1
fi

if [[ -z "$OPENJML_BIN" && -n "$OPENJML_ROOT" ]]; then
  OPENJML_BIN="$OPENJML_ROOT/openjml"
fi

if [[ -z "$JML_RUNTIME" ]]; then
  JML_RUNTIME="$OPENJML_ROOT/jmlruntime_java11.jar"
  if [[ ! -f "$JML_RUNTIME" ]]; then
    if [[ $PATCH_JMLRUNTIME -eq 1 ]]; then
      python3 "$ROOT/benchmark/SpecGenBench/stage4_jpf/patch_jmlruntime.py" \
        --src "$OPENJML_ROOT/jmlruntime.jar" \
        --dst "$OPENJML_ROOT/jmlruntime_java11.jar"
    fi
  fi
  if [[ ! -f "$JML_RUNTIME" ]]; then
    JML_RUNTIME="$OPENJML_ROOT/jmlruntime.jar"
  fi
fi

mkdir -p "$WORK_ROOT"

for strategy in "${STRATEGIES[@]}"; do
  FLAT_DIR="$OUTPUTS_ROOT/$strategy"
  if [[ ! -d "$FLAT_DIR" ]]; then
    echo "warning: skipping missing strategy dir: $FLAT_DIR" >&2
    continue
  fi

  for model in "${MODELS[@]}"; do
    RUN_ID="${strategy}__${model}"
    RUN_ROOT="$WORK_ROOT/$RUN_ID"
    SRC_ROOT="$RUN_ROOT/src"
    RAC_OUT="$RUN_ROOT/rac_javamath"
    JPF_OUT="$RUN_ROOT/jpf"

    echo "==> [$RUN_ID] preparing staged source tree"
    rm -rf "$RUN_ROOT"
    mkdir -p "$RUN_ROOT"
    python3 "$ROOT/benchmark/SpecGenBench/stage4_jpf/prepare_stage2_outputs.py" \
      --flat-dir "$FLAT_DIR" \
      --strategy "$strategy" \
      --model "$model" \
      --benchmark-root "$BENCHMARK_ROOT" \
      --out "$SRC_ROOT"

    echo "==> [$RUN_ID] Stage 3 compile"
    sh "$ROOT/benchmark/SpecGenBench/compile_common_few_shot_jml_rac_javamath.sh" \
      --src "$SRC_ROOT" \
      --out "$RAC_OUT" \
      --openjml "$OPENJML_BIN" \
      --jml-runtime "$JML_RUNTIME"

    echo "==> [$RUN_ID] Stage 4 JPF"
    JPF_ARGS=(
      python3 "$ROOT/benchmark/SpecGenBench/stage4_jpf/run_stage4_jpf.py"
      --src-root "$SRC_ROOT"
      --rac-classes "$RAC_OUT/classes"
      --jml-runtime "$JML_RUNTIME"
      --jpf-root "$JPF_ROOT"
      --openjml-java-base "$OPENJML_JAVA_BASE"
      --out "$JPF_OUT"
      --wall-timeout "$WALL_TIMEOUT"
    )
    if [[ -n "$JAVA11_HOME" ]]; then
      JPF_ARGS+=(--java11-home "$JAVA11_HOME")
    fi
    if [[ -n "$MAX_METHODS" ]]; then
      JPF_ARGS+=(--max-methods "$MAX_METHODS")
    fi
    "${JPF_ARGS[@]}"

    echo "==> [$RUN_ID] analysis"
    python3 "$ROOT/benchmark/SpecGenBench/stage4_jpf/analyze_stage34_results.py" \
      --run-root "$RUN_ROOT"

    echo "==> [$RUN_ID] done"
    echo
  done
done

echo "Stage 3/4 runs written under: $WORK_ROOT"
if [[ $SKIP_COMPARE -eq 0 ]]; then
  echo "==> cross-run comparison"
  python3 "$ROOT/benchmark/SpecGenBench/stage4_jpf/compare_stage34_runs.py" \
    --work-root "$WORK_ROOT"
fi
