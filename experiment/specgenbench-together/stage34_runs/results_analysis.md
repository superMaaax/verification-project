# SpecGenBench Stage 3/4 Results Analysis

This note summarizes the Stage 3 and Stage 4 results for four configurations:

- `zero_shot__Qwen_Qwen3-Coder-Next-FP8`
- `zero_shot__MiniMaxAI_MiniMax-M2.7`
- `few_shot__Qwen_Qwen3-Coder-Next-FP8`
- `few_shot__MiniMaxAI_MiniMax-M2.7`

The underlying comparison files are:

- [summary.csv](/Users/max/Downloads/verification-project/experiment/specgenbench-together/stage34_runs/comparisons/summary.csv)
- [compare_by_strategy.csv](/Users/max/Downloads/verification-project/experiment/specgenbench-together/stage34_runs/comparisons/compare_by_strategy.csv)
- [compare_by_model.csv](/Users/max/Downloads/verification-project/experiment/specgenbench-together/stage34_runs/comparisons/compare_by_model.csv)

## Overall Results

| Config | Stage 3 Compile OK | Stage 3 Compile Fail | Stage 4 JPF Pass | Stage 4 JPF Fail | Stage 4 Timeout | JPF Pass Rate |
| --- | ---: | ---: | ---: | ---: | ---: | ---: |
| `zero_shot__Qwen_Qwen3-Coder-Next-FP8` | 31 | 89 | 25 | 95 | 1 | 20.83% |
| `zero_shot__MiniMaxAI_MiniMax-M2.7` | 62 | 58 | 43 | 77 | 6 | 35.83% |
| `few_shot__Qwen_Qwen3-Coder-Next-FP8` | 87 | 33 | 63 | 57 | 7 | 52.50% |
| `few_shot__MiniMaxAI_MiniMax-M2.7` | 88 | 32 | 65 | 55 | 7 | 54.17% |

## Main Findings

### 1. Few-shot clearly outperformed zero-shot for both models

- For `Qwen_Qwen3-Coder-Next-FP8`, Stage 3 compile success rose from `31` to `87`, and Stage 4 JPF pass count rose from `25` to `63`.
- For `MiniMaxAI_MiniMax-M2.7`, Stage 3 compile success rose from `62` to `88`, and Stage 4 JPF pass count rose from `43` to `65`.

From [compare_by_strategy.csv](/Users/max/Downloads/verification-project/experiment/specgenbench-together/stage34_runs/comparisons/compare_by_strategy.csv):

- `Qwen`: few-shot improved Stage 3 compile success by `+56` and Stage 4 JPF passes by `+38`.
- `MiniMax`: few-shot improved Stage 3 compile success by `+26` and Stage 4 JPF passes by `+22`.

This suggests that few-shot prompting materially improves not only downstream verification results, but also the likelihood that generated JML files can be compiled by OpenJML RAC in the first place.

### 2. MiniMax was clearly stronger in zero-shot

In zero-shot:

- `MiniMaxAI_MiniMax-M2.7` achieved `62` Stage 3 compile successes and `43` Stage 4 JPF passes.
- `Qwen_Qwen3-Coder-Next-FP8` achieved `31` Stage 3 compile successes and `25` Stage 4 JPF passes.

From [compare_by_model.csv](/Users/max/Downloads/verification-project/experiment/specgenbench-together/stage34_runs/comparisons/compare_by_model.csv), moving from Qwen to MiniMax in zero-shot produced:

- `+31` Stage 3 compile successes
- `+18` Stage 4 JPF passes
- `+15.00` percentage points in Stage 4 JPF pass rate

This indicates that MiniMax has a substantially stronger zero-shot baseline on this benchmark.

### 3. In few-shot, the two models were very close

In few-shot:

- `MiniMaxAI_MiniMax-M2.7` achieved `88` Stage 3 compile successes and `65` Stage 4 JPF passes.
- `Qwen_Qwen3-Coder-Next-FP8` achieved `87` Stage 3 compile successes and `63` Stage 4 JPF passes.

From [compare_by_model.csv](/Users/max/Downloads/verification-project/experiment/specgenbench-together/stage34_runs/comparisons/compare_by_model.csv), moving from Qwen to MiniMax in few-shot produced:

- `+1` Stage 3 compile success
- `+2` Stage 4 JPF passes
- `+1.67` percentage points in Stage 4 JPF pass rate

So MiniMax remains slightly ahead, but the gap largely disappears once few-shot prompting is used.

## Failure Pattern Notes

Per-run `violation_types` from each `analysis_summary.json` show that a large fraction of Stage 4 failures are actually `skipped`, meaning the benchmark did not reach JPF verification because Stage 3 RAC compilation failed.

### Zero-shot Qwen

- `skipped`: `91`
- `NoUncaughtExceptionsProperty`: `3`
- `exit_code_-9`: `1`

### Zero-shot MiniMax

- `skipped`: `59`
- `NoUncaughtExceptionsProperty`: `12`
- `exit_code_-9`: `6`

### Few-shot Qwen

- `skipped`: `36`
- `NoUncaughtExceptionsProperty`: `15`
- `JavaException`: `2`
- `exit_code_-9`: `4`

### Few-shot MiniMax

- `skipped`: `34`
- `NoUncaughtExceptionsProperty`: `15`
- `exit_code_-9`: `6`

This reinforces an important interpretation: the largest gains from few-shot prompting come from moving more generated files into the compilable subset. Once a file compiles, both models often achieve meaningful JPF exploration, as shown by the large number of `pass_kind=A` results.

## Pass Quality

Pass-kind counts from the Stage 4 summaries:

- `zero_shot__Qwen_Qwen3-Coder-Next-FP8`: `A=24`, `B=1`
- `zero_shot__MiniMaxAI_MiniMax-M2.7`: `A=41`, `B=2`
- `few_shot__Qwen_Qwen3-Coder-Next-FP8`: `A=61`, `B=2`
- `few_shot__MiniMaxAI_MiniMax-M2.7`: `A=63`, `B=2`

Most passing runs are `pass_kind=A`, which means they are not just trivial shallow executions. This makes the few-shot improvements more meaningful, because the gains are concentrated in substantive successful verifications rather than only weak or trivial passes.

## Bottom-Line Interpretation

- Few-shot prompting is the strongest lever in this experiment.
- `Qwen_Qwen3-Coder-Next-FP8` benefits more dramatically from few-shot than MiniMax does.
- `MiniMaxAI_MiniMax-M2.7` has the stronger zero-shot baseline.
- In few-shot mode, the two models are nearly tied, with MiniMax holding a small edge.
- Stage 3 compileability is still a major bottleneck, so downstream Stage 4 outcomes should be interpreted together with Stage 3 results rather than in isolation.

## Suggested Follow-Up Analysis

- Categorize Stage 3 compile failures from `rac_javamath/compile_logs/*.log`.
- Separate Stage 4 true verification failures from Stage 4 `skipped` rows.
- Inspect recurring `NoUncaughtExceptionsProperty` traces to identify common specification mistakes.
- Compare results normalized over only the Stage 3 compilable subset.
