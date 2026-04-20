#!/bin/sh

set -eu

ROOT="$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)"
JAVA11_HOME="$(/usr/libexec/java_home -v 11)"
JPF_DIR="$ROOT/.tools/jpf-core"
STUB_VERIFIER="$ROOT/pipeline-smoke-test/jpf-regression/stubs/org/sosy_lab/sv_benchmarks/Verifier.java"

mkdir -p "$HOME/.jpf"
cat > "$HOME/.jpf/site.properties" <<EOF
jpf-core=$JPF_DIR
extensions=\${jpf-core}
EOF

build_case() {
  case_name="$1"
  build_dir="$ROOT/pipeline-smoke-test/jpf-regression/$case_name/build"
  src_dir="$ROOT/sv-benchmarks/java/jpf-regression/$case_name"
  rm -rf "$build_dir"
  mkdir -p "$build_dir"
  javac --release 11 -d "$build_dir" "$STUB_VERIFIER" "$src_dir/Main.java"
}

run_case() {
  case_name="$1"
  case_dir="$ROOT/pipeline-smoke-test/jpf-regression/$case_name"
  echo
  echo "=== $case_name ==="
  cd "$case_dir"
  "$JAVA11_HOME/bin/java" -Xmx1024m -jar "$JPF_DIR/build/RunJPF.jar" "$case_name.jpf"
}

build_case ExSymExeSimple_true
build_case ExSymExeSimple_false

run_case ExSymExeSimple_true
run_case ExSymExeSimple_false
