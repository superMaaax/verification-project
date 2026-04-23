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
- Stage 2 experiment-output adapters:
  - `benchmark/SpecGenBench/run_stage3_stage4_from_experiment_outputs.sh`
  - `benchmark/SpecGenBench/stage4_jpf/prepare_stage2_outputs.py`
  - `benchmark/SpecGenBench/stage4_jpf/analyze_stage34_results.py`
  - `benchmark/SpecGenBench/stage4_jpf/compare_stage34_runs.py`

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

If your Stage 2 JML comes from the Together experiment outputs instead of
`common_few_shot_jml`, the new adapter script below stages them into the tree
layout expected by Stage 3/4.

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

The same script now also accepts custom input/output trees:

```bash
sh benchmark/SpecGenBench/compile_common_few_shot_jml_rac_javamath.sh \
  --src /path/to/staged_jml_tree \
  --out /path/to/rac_output
```

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

## Stage 3-4 directly from experiment outputs

If your Stage 2 results live under:

- `experiment/specgenbench-together/outputs/zero_shot/`
- `experiment/specgenbench-together/outputs/few_shot/`

you can run the whole Stage 3-4 pipeline from repo root with:

```bash
bash benchmark/SpecGenBench/run_stage3_stage4_from_experiment_outputs.sh
```

This script:

1. stages flat `*__code.java` files into a `Method/Method.java` tree,
2. runs Stage 3 OpenJML RAC compilation,
3. runs Stage 4 JPF verification,
4. writes an `analysis_summary.json` for each model/strategy configuration.
5. writes cross-run comparison CSVs unless `--skip-compare` is passed.

Example: run only MiniMax few-shot

```bash
bash benchmark/SpecGenBench/run_stage3_stage4_from_experiment_outputs.sh \
  --strategy few_shot \
  --model MiniMaxAI_MiniMax-M2.7
```

Example: run both models for both strategies with a larger Stage 4 timeout

```bash
bash benchmark/SpecGenBench/run_stage3_stage4_from_experiment_outputs.sh \
  --model Qwen_Qwen3-Coder-Next-FP8 \
  --model MiniMaxAI_MiniMax-M2.7 \
  --wall-timeout 45
```

Outputs are written under:

- staged run roots:
  - `experiment/specgenbench-together/stage34_runs/<strategy>__<model>/`
- staged sources:
  - `.../src/`
- RAC compile outputs:
  - `.../rac_javamath/`
- JPF outputs:
  - `.../jpf/results/jpf_verification.csv`
- per-run analysis:
  - `.../analysis_summary.json`
- cross-run comparisons:
  - `.../comparisons/summary.csv`
  - `.../comparisons/compare_by_strategy.csv`
  - `.../comparisons/compare_by_model.csv`

`compare_by_strategy.csv` compares `zero_shot -> few_shot` within the same
model. `compare_by_model.csv` compares
`Qwen_Qwen3-Coder-Next-FP8 -> MiniMaxAI_MiniMax-M2.7` within the same strategy.

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
