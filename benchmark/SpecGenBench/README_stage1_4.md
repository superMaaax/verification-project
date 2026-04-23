# SpecGenBench Stage 1-4 Repro Guide

This guide is for teammates who clone the branch and want to either:

1. quickly inspect Stage 3/4 outputs, or
2. rerun the Stage 1-4 pipeline end-to-end.

## What is in this branch

- Stage 3 (OpenJML RAC, java-math mode) script:
  - `benchmark/SpecGenBench/compile_common_few_shot_jml_rac_javamath.sh`
- Stage 4 (JPF batch runner + CSV export) scripts:
  - `benchmark/SpecGenBench/stage4_jpf/run_stage4_jpf.py`
  - `benchmark/SpecGenBench/stage4_jpf/run_stage4_jpf.sh`
  - `benchmark/SpecGenBench/stage4_jpf/patch_jmlruntime.py`

## Prerequisites

- macOS or Linux shell
- Python 3
- JDK 11
- OpenJML extracted under `.tools/openjml`
- JPF core under `.tools/jpf-core` with classfile limit patch (`MAX_SUPPORTED_VERSION=60`) and built jars

Expected key files:

- `.tools/openjml/openjml`
- `.tools/openjml/jmlruntime.jar`
- `.tools/jpf-core/build/RunJPF.jar`
- `.tools/jpf-core/build/jpf-classes.jar`
- `.tools/jpf-core/build/jpf.jar`

## Stage 1-2 context

- Stage 1 input benchmarks:
  - `benchmark/SpecGenBench/common/`
- Stage 2 generated JML sources (few-shot):
  - `benchmark/SpecGenBench/common_few_shot_jml/`

If you only want Stage 3-4 reproducibility, you can start directly from Stage 3.

## Stage 3: Compile RAC bytecode (java math)

Run from repo root:

```bash
sh benchmark/SpecGenBench/compile_common_few_shot_jml_rac_javamath.sh
```

Output:

- RAC classes:
  - `benchmark/SpecGenBench/common_few_shot_jml_rac_javamath/classes/`
- compile summary:
  - `benchmark/SpecGenBench/common_few_shot_jml_rac_javamath/compile_logs/summary.tsv`
- per-file logs:
  - `benchmark/SpecGenBench/common_few_shot_jml_rac_javamath/compile_logs/*.log`

## Optional compatibility patch: jmlruntime -> Java 11 class version

Run:

```bash
python3 benchmark/SpecGenBench/stage4_jpf/patch_jmlruntime.py
```

This writes:

- `.tools/openjml/jmlruntime_java11.jar`

## Stage 4: JPF verification batch + CSV

Run from repo root:

```bash
python3 benchmark/SpecGenBench/stage4_jpf/run_stage4_jpf.py \
  --rac-classes benchmark/SpecGenBench/common_few_shot_jml_rac_javamath/classes \
  --jml-runtime .tools/openjml/jmlruntime_java11.jar \
  --openjml-java-base .tools/openjml/NO_JAVA_BASE \
  --out benchmark/SpecGenBench/stage4_common_few_shot_jpf_javamath \
  --wall-timeout 30
```

Notes:

- `--openjml-java-base` is intentionally set to a non-existent path here, so it is not added to JPF classpath.
- Per-method JPF files and harnesses are generated automatically.
- Timeout is per-method wall-clock timeout.

Output:

- CSV results:
  - `benchmark/SpecGenBench/stage4_common_few_shot_jpf_javamath/results/jpf_verification.csv`
- full traces for failed/timeout runs:
  - `benchmark/SpecGenBench/stage4_common_few_shot_jpf_javamath/results/traces/*.txt`
- generated per-method `.jpf`:
  - `benchmark/SpecGenBench/stage4_common_few_shot_jpf_javamath/jpf/*.jpf`

## Quick sanity checks

```bash
wc -l benchmark/SpecGenBench/common_few_shot_jml_rac_javamath/compile_logs/summary.tsv
wc -l benchmark/SpecGenBench/stage4_common_few_shot_jpf_javamath/results/jpf_verification.csv
```

## Troubleshooting

- If Stage 4 says missing JPF jars, build JPF:
  - set `JAVA_HOME` to JDK 11
  - run `./gradlew buildJars` in `.tools/jpf-core`
- If Stage 4 fails with class version errors, ensure `jmlruntime_java11.jar` exists and is used via `--jml-runtime`.
- If methods timeout frequently, reduce harness domains or increase `--wall-timeout`.
