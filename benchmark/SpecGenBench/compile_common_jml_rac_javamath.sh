#!/usr/bin/env sh
# Parameterized version of Zhong's compile_common_few_shot_jml_rac_javamath.sh
#
# Usage:
#   sh compile_common_jml_rac_javamath.sh <strategy> [SRC] [OUT]
#
# Defaults (when SRC/OUT omitted):
#   reads:   benchmark/SpecGenBench/common_<strategy>_jml/
#   writes:  benchmark/SpecGenBench/common_<strategy>_jml_rac_javamath/
#
# Examples:
#   sh compile_common_jml_rac_javamath.sh few_shot
#   sh compile_common_jml_rac_javamath.sh zero_shot
#   sh compile_common_jml_rac_javamath.sh zero_shot \
#        smoke-test/stage3-jml/zero_shot smoke-test/stage3-rac/zero_shot

set -eu

if [ $# -lt 1 ]; then
  echo "usage: $0 <strategy> [SRC] [OUT]" >&2
  exit 2
fi
STRATEGY="$1"

ROOT="$(CDPATH= cd -- "$(dirname -- "$0")/../.." && pwd)"
DEFAULT_SRC="$ROOT/benchmark/SpecGenBench/common_${STRATEGY}_jml"
DEFAULT_OUT="$ROOT/benchmark/SpecGenBench/common_${STRATEGY}_jml_rac_javamath"
SRC="${2:-$DEFAULT_SRC}"
OUT="${3:-$DEFAULT_OUT}"
case "$SRC" in /*) ;; *) SRC="$ROOT/$SRC" ;; esac
case "$OUT" in /*) ;; *) OUT="$ROOT/$OUT" ;; esac
CLASSES="$OUT/classes"
LOGDIR="$OUT/compile_logs"
SUMMARY="$LOGDIR/summary.tsv"

if [ ! -d "$SRC" ]; then
  echo "error: source dir not found: $SRC" >&2
  echo "       did you run reshape_outputs_to_stage3_layout.py first?" >&2
  exit 1
fi

if test -x "$ROOT/.tools/openjml/openjml" && test -f "$ROOT/.tools/openjml/jmlruntime.jar"; then
  OPENJML="$ROOT/.tools/openjml/openjml"
  JMLRT="$ROOT/.tools/openjml/jmlruntime.jar"
else
  echo "error: OpenJML not found under $ROOT/.tools/openjml" >&2
  exit 1
fi

rm -rf "$OUT"
mkdir -p "$CLASSES" "$LOGDIR"
cp -f "$JMLRT" "$OUT/jmlruntime.jar"

: >"$SUMMARY"

while IFS= read -r f; do
  test -f "$f" || continue
  base="$(basename "$f" .java)"
  log="$LOGDIR/${base}.log"
  set +e
  "$OPENJML" --rac -source 11 -target 11 -spec-math=java -code-math=java -cp "$JMLRT" -d "$CLASSES" "$f" >"$log" 2>&1
  ec=$?
  set -e
  rel="${f#"$SRC"/}"
  if test "$ec" -eq 0; then
    printf '%s\t%s\tok\n' "$rel" "$base" >>"$SUMMARY"
  else
    printf '%s\t%s\tfail\texit=%s\n' "$rel" "$base" "$ec" >>"$SUMMARY"
  fi
done <<EOF2
$(find "$SRC" -name '*.java' -print | LC_ALL=C sort)
EOF2

OK="$(awk -F'\t' '$3 == "ok" { n++ } END { print n+0 }' "$SUMMARY")"
FAIL="$(awk -F'\t' '$3 == "fail" { n++ } END { print n+0 }' "$SUMMARY")"

echo "Strategy: $STRATEGY"
echo "OpenJML RAC (java math): ok=$OK fail=$FAIL"
echo "Bytecode: $CLASSES"
echo "JML runtime: $OUT/jmlruntime.jar"
echo "Logs: $LOGDIR"
echo "Summary: $SUMMARY"
