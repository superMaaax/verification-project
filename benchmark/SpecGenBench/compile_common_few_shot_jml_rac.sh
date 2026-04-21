#!/usr/bin/env sh
# Compile every Java source under common_few_shot_jml/ with OpenJML RAC.
# Class files (with embedded JML runtime checks) go to common_few_shot_jml_rac/classes/.
# Per-file compiler output: common_few_shot_jml_rac/compile_logs/<ClassName>.log
# Summary: common_few_shot_jml_rac/compile_logs/summary.tsv
#
# Prerequisites (override with OPENJML_ROOT, OPENJML, JMLRT):
#   $REPO_ROOT/.tools/openjml/openjml
#   $REPO_ROOT/.tools/openjml/jmlruntime.jar
# Or:
#   $REPO_ROOT/.tools/openjml  (executable)
#   $REPO_ROOT/.tools/jmlruntime.jar
#
# Matches pipeline-smoke-test/run_findfirstzero.sh: --rac -source 11 -target 11

set -eu

ROOT="$(CDPATH= cd -- "$(dirname -- "$0")/../.." && pwd)"
SRC="$ROOT/benchmark/SpecGenBench/common_few_shot_jml"
OUT="$ROOT/benchmark/SpecGenBench/common_few_shot_jml_rac"
CLASSES="$OUT/classes"
LOGDIR="$OUT/compile_logs"
SUMMARY="$LOGDIR/summary.tsv"

if test -n "${OPENJML_ROOT:-}"; then
  OPENJML="${OPENJML:-$OPENJML_ROOT/openjml}"
  JMLRT="${JMLRT:-$OPENJML_ROOT/jmlruntime.jar}"
elif test -x "$ROOT/.tools/openjml/openjml" && test -f "$ROOT/.tools/openjml/jmlruntime.jar"; then
  OPENJML_ROOT="$ROOT/.tools/openjml"
  OPENJML="${OPENJML:-$OPENJML_ROOT/openjml}"
  JMLRT="${JMLRT:-$OPENJML_ROOT/jmlruntime.jar}"
elif test -x "$ROOT/.tools/openjml" && test -f "$ROOT/.tools/jmlruntime.jar"; then
  OPENJML_ROOT="$ROOT/.tools"
  OPENJML="${OPENJML:-$OPENJML_ROOT/openjml}"
  JMLRT="${JMLRT:-$OPENJML_ROOT/jmlruntime.jar}"
else
  echo "error: OpenJML not found. Expected either:" >&2
  echo "  $ROOT/.tools/openjml/openjml and jmlruntime.jar in the same directory, or" >&2
  echo "  $ROOT/.tools/openjml (executable) and $ROOT/.tools/jmlruntime.jar" >&2
  echo "Set OPENJML_ROOT to the directory containing both openjml and jmlruntime.jar" >&2
  exit 1
fi

if ! test -x "$OPENJML"; then
  echo "error: not executable: $OPENJML" >&2
  exit 1
fi
if ! test -f "$JMLRT"; then
  echo "error: missing jmlruntime.jar: $JMLRT" >&2
  exit 1
fi
if ! test -d "$SRC"; then
  echo "error: missing annotated sources: $SRC" >&2
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
  "$OPENJML" --rac -source 11 -target 11 -cp "$JMLRT" -d "$CLASSES" "$f" >"$log" 2>&1
  ec=$?
  set -e
  rel="${f#"$SRC"/}"
  if test "$ec" -eq 0; then
    printf '%s\t%s\tok\n' "$rel" "$base" >>"$SUMMARY"
  else
    printf '%s\t%s\tfail\texit=%s\n' "$rel" "$base" "$ec" >>"$SUMMARY"
  fi
done <<EOF
$(find "$SRC" -name '*.java' -print | LC_ALL=C sort)
EOF

OK="$(awk -F'	' '$3 == "ok" { n++ } END { print n+0 }' "$SUMMARY")"
FAIL="$(awk -F'	' '$3 == "fail" { n++ } END { print n+0 }' "$SUMMARY")"

echo "OpenJML RAC: ok=$OK fail=$FAIL"
echo "Bytecode: $CLASSES"
echo "JML runtime (for java -cp): $OUT/jmlruntime.jar"
echo "Logs: $LOGDIR"
echo "Summary: $SUMMARY"
