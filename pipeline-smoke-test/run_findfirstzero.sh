#!/bin/sh

set -eu

ROOT="$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)"
TEST_DIR="$ROOT/pipeline-smoke-test/FindFirstZero"
JPF_DIR="$ROOT/.tools/jpf-core"
OPENJML_DIR="$ROOT/.tools/openjml"
JAVA11_HOME="$(/usr/libexec/java_home -v 11)"

mkdir -p "$HOME/.jpf"
cat > "$HOME/.jpf/site.properties" <<EOF
jpf-core=$JPF_DIR
extensions=\${jpf-core}
EOF

cd "$ROOT"

# Stage 3: OpenJML RAC (requires jmlruntime.jar; emits Java 16 classfiles — use patched jpf-core ClassFile.MAX_SUPPORTED_VERSION)
JMLRT="$OPENJML_DIR/jmlruntime.jar"
rm -rf "$TEST_DIR/build"
mkdir -p "$TEST_DIR/build"

"$OPENJML_DIR/openjml" --rac -source 11 -target 11 \
  -cp "$JPF_DIR/build/jpf-classes.jar:$JPF_DIR/build/jpf.jar:$JMLRT" \
  -d "$TEST_DIR/build" \
  "$TEST_DIR/FindFirstZero.java" \
  "$TEST_DIR/FindFirstZeroChecked.java" \
  "$TEST_DIR/FindFirstZeroDriver.java"

cd "$TEST_DIR"
"$JAVA11_HOME/bin/java" -Xmx1024m -jar "$JPF_DIR/build/RunJPF.jar" \
  +report.console.finished=statistics \
  +report.console.property_violation=error,trace \
  FindFirstZero.jpf
