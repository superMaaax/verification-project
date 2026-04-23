#!/usr/bin/env sh
set -eu

ROOT="$(CDPATH= cd -- "$(dirname -- "$0")/../.." && pwd)"
SRC="$ROOT/benchmark/SpecGenBench/common_few_shot_jml"
OUT="$ROOT/benchmark/SpecGenBench/common_few_shot_jml_rac_javamath"
CLASSES="$OUT/classes"
LOGDIR="$OUT/compile_logs"
SUMMARY="$LOGDIR/summary.tsv"

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

echo "OpenJML RAC (java math): ok=$OK fail=$FAIL"
echo "Bytecode: $CLASSES"
echo "JML runtime: $OUT/jmlruntime.jar"
echo "Logs: $LOGDIR"
echo "Summary: $SUMMARY"
