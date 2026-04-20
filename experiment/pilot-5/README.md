# Pilot 5 Workflow

This pilot evaluates three prompting strategies for JML generation on five
SpecGenBench methods:

- `Absolute`
- `AddLoop`
- `FindFirstZero`
- `LinearSearch`
- `Smallest`

The goal of this pilot is not full-scale automation yet. It is a controlled
small run to compare:

- `zero-shot`
- `few-shot`
- `cot`

## Dataset

The benchmark inputs are copied from `SpecGen-Artifact/benchmark/SpecGenBench/common/`.

The reference oracles remain in `SpecGen-Artifact/prompts/oracle/` and should be
used only for evaluation, not as target inputs.

## Folder Layout

- `benchmarks/`: source programs to annotate
- `prompts/`: prompt templates
- `outputs/zero_shot/`: raw model outputs
- `outputs/few_shot/`: raw model outputs
- `outputs/cot/`: raw model outputs
- `results/`: summary tables and notes

## Recommended Procedure

1. Pick one benchmark file from `benchmarks/`.
2. Fill the selected prompt template with that file's source code.
3. Run the same model and temperature for all three prompting strategies.
4. Save the raw response in the matching `outputs/` folder.
5. Extract the generated Java+JML code from the response.
6. Verify it with your chosen checker.
7. Record the result in `results/pilot-results.csv`.

## Fair Comparison Rules

- Use the same model for all three strategies.
- Use the same temperature for all three strategies.
- Use one-shot generation only for this pilot.
- Do not feed verifier feedback back into the model during this comparison.
- Keep the few-shot examples fixed across all five methods.

## Suggested Verification Fields

- `method`
- `strategy`
- `model`
- `syntax_ok`
- `compiled_or_parsed`
- `verification_pass`
- `error_type`
- `notes`
