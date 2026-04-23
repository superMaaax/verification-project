#!/usr/bin/env sh
set -eu

ROOT="$(CDPATH= cd -- "$(dirname -- "$0")/../.." && pwd)"
SRC="$ROOT/benchmark/SpecGenBench/common_few_shot_jml"
OUT="$ROOT/benchmark/SpecGenBench/common_few_shot_jml_rac_javamath"
OPENJML="${OPENJML:-}"
JMLRT="${JMLRT:-}"
OPENJML_ROOT="${OPENJML_ROOT:-}"

usage() {
  cat <<EOF
Usage:
  sh benchmark/SpecGenBench/compile_common_few_shot_jml_rac_javamath.sh [options]

Options:
  --src PATH          Source tree containing benchmark Java files.
  --out PATH          Output directory for RAC classes and compile logs.
  --openjml PATH      Path to the OpenJML executable.
  --jml-runtime PATH  Path to jmlruntime.jar.
  --help              Show this help.

Defaults preserve the historical Stage 3 behavior for common_few_shot_jml.
EOF
}

while test "$#" -gt 0; do
  case "$1" in
    --src)
      SRC="$2"
      shift 2
      ;;
    --out)
      OUT="$2"
      shift 2
      ;;
    --openjml)
      OPENJML="$2"
      shift 2
      ;;
    --jml-runtime)
      JMLRT="$2"
      shift 2
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

if test -n "$OPENJML_ROOT"; then
  if test -z "$OPENJML"; then
    OPENJML="$OPENJML_ROOT/openjml"
  fi
  if test -z "$JMLRT"; then
    JMLRT="$OPENJML_ROOT/jmlruntime.jar"
  fi
elif test -z "$OPENJML" && test -z "$JMLRT" && test -x "$ROOT/.tools/openjml/openjml" && test -f "$ROOT/.tools/openjml/jmlruntime.jar"; then
  OPENJML_ROOT="$ROOT/.tools/openjml"
  OPENJML="$OPENJML_ROOT/openjml"
  JMLRT="$OPENJML_ROOT/jmlruntime.jar"
elif test -z "$OPENJML" && test -z "$JMLRT" && test -x "$ROOT/.tools/openjml" && test -f "$ROOT/.tools/jmlruntime.jar"; then
  OPENJML_ROOT="$ROOT/.tools"
  OPENJML="$OPENJML_ROOT/openjml"
  JMLRT="$OPENJML_ROOT/jmlruntime.jar"
fi

if ! test -x "$OPENJML"; then
  echo "error: OpenJML executable not found: $OPENJML" >&2
  echo "hint: pass --openjml PATH / --openjml-root PATH, or set OPENJML / OPENJML_ROOT" >&2
  exit 1
fi
if ! test -f "$JMLRT"; then
  echo "error: JML runtime jar not found: $JMLRT" >&2
  echo "hint: pass --jml-runtime PATH, or set JMLRT / OPENJML_ROOT" >&2
  exit 1
fi
if ! test -d "$SRC"; then
  echo "error: source tree not found: $SRC" >&2
  exit 1
fi

CLASSES="$OUT/classes"
LOGDIR="$OUT/compile_logs"
SUMMARY="$LOGDIR/summary.tsv"

rm -rf "$OUT"
mkdir -p "$CLASSES" "$LOGDIR"
cp -f "$JMLRT" "$OUT/jmlruntime.jar"

: >"$SUMMARY"

FOUND_ANY=0
while IFS= read -r f; do
  test -f "$f" || continue
  FOUND_ANY=1
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

if test "$FOUND_ANY" -eq 0; then
  echo "error: no Java files found under $SRC" >&2
  exit 1
fi

OK="$(awk -F'\t' '$3 == "ok" { n++ } END { print n+0 }' "$SUMMARY")"
FAIL="$(awk -F'\t' '$3 == "fail" { n++ } END { print n+0 }' "$SUMMARY")"

echo "OpenJML RAC (java math): ok=$OK fail=$FAIL"
echo "Source tree: $SRC"
echo "Bytecode: $CLASSES"
echo "JML runtime: $OUT/jmlruntime.jar"
echo "Logs: $LOGDIR"
echo "Summary: $SUMMARY"
