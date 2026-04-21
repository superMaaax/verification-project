#!/usr/bin/env sh
# Stage 4 — run JPF over RAC classes for common_few_shot_jml (see run_stage4_jpf.py).
set -eu
ROOT="$(CDPATH= cd -- "$(dirname -- "$0")/../../.." && pwd)"
exec python3 "$ROOT/benchmark/SpecGenBench/stage4_jpf/run_stage4_jpf.py" "$@"
