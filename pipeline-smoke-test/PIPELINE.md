# Evaluation pipeline (LLM JML + OpenJML RAC + JPF)

This document describes the **current end-to-end pipeline** used in this repository for evaluating JML-annotated Java with **OpenJML runtime assertion checking (RAC)** and **Java Pathfinder (JPF)**. The `FindFirstZero` smoke test is the reference implementation; larger experiments (e.g. `experiment/pilot-5/`) follow the same logical stages.

---

## Stages (overview)

```
┌─────────────────────────────────────────────────────────────────┐
│  Stage 1: Dataset                                               │
│  Self-contained methods (e.g. SpecGenBench–style benchmarks).     │
└──────────────────────────┬──────────────────────────────────────┘
                           ↓
┌──────────────────────────┴──────────────────────────────────────┐
│  Stage 2: LLM specification generation                            │
│  Prompted model outputs full Java sources with JML (`requires`,   │
│  `ensures`, loop specs, etc.).                                   │
└──────────────────────────┬──────────────────────────────────────┘
                           ↓
┌──────────────────────────┴──────────────────────────────────────┐
│  Stage 3: Compilation (OpenJML RAC)                              │
│  OpenJML `--rac` compiles JML into runtime checks in bytecode.   │
│  Requires `jmlruntime.jar` on the compile classpath.               │
│  Emits Java 16–level classfiles (major version 60) in practice. │
└──────────────────────────┬──────────────────────────────────────┘
                           ↓
┌──────────────────────────┴──────────────────────────────────────┐
│  Stage 4: Verification (JPF)                                     │
│  JPF explores the program under test; RAC failures surface as    │
│  assertion / contract violations during exploration.              │
│  `jmlruntime.jar` must also be on JPF’s application classpath.   │
└──────────────────────────┬──────────────────────────────────────┘
                           ↓
┌──────────────────────────┴──────────────────────────────────────┐
│  Stage 5: Evaluation                                             │
│  Record pass / fail / error; collect traces and counterexamples; │
│  optionally track syntax, compile, and verification rates.      │
└─────────────────────────────────────────────────────────────────┘
```

---

## Toolchain layout

| Component | Location |
|-----------|----------|
| OpenJML (includes bundled compiler used for RAC) | `.tools/openjml/openjml` |
| JML runtime (RAC support classes, e.g. `org.jmlspecs.runtime.*`) | `.tools/openjml/jmlruntime.jar` |
| JPF core | `.tools/jpf-core/` |
| JPF launcher | `.tools/jpf-core/build/RunJPF.jar` |
| Host JVM for JPF | **Java 11** recommended (`/usr/libexec/java_home -v 11` on macOS) |

### JPF classfile limit (required for this OpenJML build)

Stock JPF 8 accepts classfiles only up to **major version 55** (Java 11). The OpenJML distribution bundled here emits **RAC bytecode at major version 60** (Java 16) even when `-source 11 -target 11` is set. This repository therefore patches:

` .tools/jpf-core/src/main/gov/nasa/jpf/jvm/ClassFile.java` — `MAX_SUPPORTED_VERSION` raised to **60**.

After changing JPF sources, rebuild with **JDK 11** as `JAVA_HOME`:

```bash
export JAVA_HOME="$(/usr/libexec/java_home -v 11)"
cd .tools/jpf-core
./gradlew buildJars
```

---

## Stage 2 — Together AI batch generation

For the full `SpecGenBench/common` scale-up, use the repository-level batch
driver:

```bash
export TOGETHER_API_KEY="..."

python3 run_specgenbench_together.py \
  --strategy zero_shot \
  --model Qwen/Qwen3-Coder-Next-FP8 \
  --concurrency 2
```

The default input directory is:

```text
SpecGen-Artifact/benchmark/SpecGenBench/common
```

The default prompt templates are:

```text
experiment/pilot-5/prompts/zero_shot.txt
experiment/pilot-5/prompts/few_shot.txt
experiment/pilot-5/prompts/cot.txt
```

Outputs are written under:

```text
experiment/specgenbench-together/
```

For a no-cost sanity check, render prompts without calling Together:

```bash
python3 run_specgenbench_together.py --dry-run --methods FindFirstZero --strategy zero_shot
```

Each generated benchmark records:

- the rendered prompt in `prompts_rendered/<strategy>/`
- the raw model response in `outputs/<strategy>/`
- the extracted Java source in `outputs/<strategy>/`
- run metadata in `results/<strategy>__<model>__metadata.jsonl`

Use `--strategy few_shot` or `--strategy cot` for the other prompt types once
the zero-shot run is complete.

---

## Stage 3 — OpenJML RAC

**Purpose:** Produce instrumented bytecode under `build/` so JML contracts become executable checks.

**Requirements:**

1. Include **all** application sources that participate in the run (for `FindFirstZero`: `FindFirstZero.java`, `FindFirstZeroChecked.java`, `FindFirstZeroDriver.java`).
2. Put **JPF’s** `jpf-classes.jar` and `jpf.jar` on `-cp` so `FindFirstZeroDriver` can resolve `gov.nasa.jpf.vm.Verify`.
3. Append **`jmlruntime.jar`** to `-cp` so RAC-generated code can link the JML runtime.

**Illustrative command** (paths from repository root):

```bash
ROOT="/path/to/verification_project"
TEST_DIR="$ROOT/pipeline-smoke-test/FindFirstZero"
JPF_DIR="$ROOT/.tools/jpf-core"
OPENJML_DIR="$ROOT/.tools/openjml"
JMLRT="$OPENJML_DIR/jmlruntime.jar"

rm -rf "$TEST_DIR/build" && mkdir -p "$TEST_DIR/build"

"$OPENJML_DIR/openjml" --rac -source 11 -target 11 \
  -cp "$JPF_DIR/build/jpf-classes.jar:$JPF_DIR/build/jpf.jar:$JMLRT" \
  -d "$TEST_DIR/build" \
  "$TEST_DIR/FindFirstZero.java" \
  "$TEST_DIR/FindFirstZeroChecked.java" \
  "$TEST_DIR/FindFirstZeroDriver.java"
```

---

## Stage 4 — JPF

**Purpose:** Model-check the entrypoint (here `FindFirstZeroDriver`) against bounded nondeterminism from `Verify`.

**JPF configuration** (`FindFirstZero/FindFirstZero.jpf`):

- `target=FindFirstZeroDriver`
- `classpath` must include both the RAC output directory **`build`** and **`jmlruntime.jar`** (relative path from the `.jpf` file location):

```properties
classpath=build:../../.tools/openjml/jmlruntime.jar
```

**Run** (from the directory that contains `FindFirstZero.jpf`):

```bash
JAVA11_HOME="$(/usr/libexec/java_home -v 11)"
"$JAVA11_HOME/bin/java" -Xmx1024m -jar "$ROOT/.tools/jpf-core/build/RunJPF.jar" \
  +report.console.finished=statistics \
  +report.console.property_violation=error,trace \
  FindFirstZero.jpf
```

**Site config:** ensure `$HOME/.jpf/site.properties` points `jpf-core` at `.tools/jpf-core` (the provided `run_findfirstzero.sh` writes this).

---

## Stage 5 — Evaluation

Interpret JPF’s report:

- **No RAC/JML violation:** search completes; statistics show explored states.
- **Contract violation:** output includes `verify: JML ...` messages and/or property-violation sections with traces — treat as **fail** with a concrete path (counterexample).
- **Tool errors:** e.g. missing `jmlruntime.jar` → `ClassNotFoundException` for `org.jmlspecs.runtime.*`; wrong classfile version → load/parse errors until JPF is rebuilt with the patch above.

For pilots, results can be logged in CSV form (see `experiment/pilot-5/results/pilot-results.csv` and the suggested columns in `experiment/pilot-5/README.md`).

---

## One-shot script (smoke test)

From the repository root:

```bash
sh pipeline-smoke-test/run_findfirstzero.sh
```

This performs **Stage 3 (RAC)** then **Stage 4 (JPF)** for `pipeline-smoke-test/FindFirstZero/`.

---

## Relationship to `progress-report.md`

The written report describes the **same logical pipeline** (dataset → LLM → compile with OpenJML → JPF → evaluation). The implementation detail that matters in practice is that **RAC** in this repo uses **Java 16 bytecode** and **requires `jmlruntime.jar` at both compile time and JPF runtime**, plus the **JPF `ClassFile` patch** above, so that Stages 3–4 are reproducible on the current tool versions.
