# Progress Update

**Project**: Empirical Evaluation of LLM-Synthesized JML Using Java PathFinder
**Team**: Minchao Huang, Xiangxin Zhong, Tiancheng Xiao

---

## Project Overview

This project evaluates whether standard prompting techniques can enable LLMs to generate accurate JML preconditions and postconditions for self-contained Java methods. Java PathFinder (JPF) serves as an automated dynamic oracle — it systematically explores the execution state space of annotated methods to detect contract violations. Unlike prior work that relies on deductive verification (OpenJML ESC, KeY), JPF provides execution-based verification that can produce concrete counterexamples and is less susceptible to vacuous specifications.

## Evaluation Pipeline

The project follows a five-stage automated pipeline:

```
 ┌─────────────────────────────────────────────────────────────────┐
 │  Stage 1: Dataset                                              │
 │  Select self-contained Java methods from SpecGenBench│
 └──────────────────────────┬──────────────────────────────────────┘
                            ↓
 ┌──────────────────────────┴──────────────────────────────────────┐
 │  Stage 2: LLM Specification Generation                         │
 │  Prompt LLM (zero-shot / few-shot / CoT) to generate JML      │
 │  annotated Java source (requires + ensures clauses)            │
 └──────────────────────────┬──────────────────────────────────────┘
                            ↓
 ┌──────────────────────────┴──────────────────────────────────────┐
 │  Stage 3: Compilation                                          │
 │  OpenJML (RAC mode) compiles JML annotations into executable   │
 │  runtime assertions embedded in Java bytecode                  │
 └──────────────────────────┬──────────────────────────────────────┘
                            ↓
 ┌──────────────────────────┴──────────────────────────────────────┐
 │  Stage 4: Verification                                         │
 │  JPF loads instrumented bytecode and systematically explores   │
 │  execution paths to detect assertion violations                │
 └──────────────────────────┬──────────────────────────────────────┘
                            ↓
 ┌──────────────────────────┴──────────────────────────────────────┐
 │  Stage 5: Evaluation                                           │
 │  Collect results: pass / fail (with counterexample) / error    │
 │  Analyze: syntax correctness, JPF pass rate, error taxonomy    │
 └─────────────────────────────────────────────────────────────────┘
```

**Key design decision**: The LLM outputs complete JML-annotated Java source code directly (not separate JML fragments), eliminating the need for a manual annotation insertion step. OpenJML is used here purely as a compiler (RAC mode), not as a verifier — the actual verification is performed by JPF.

## What We Did 

**Environment**: We installed and configured the core toolchain — OpenJML for compiling JML annotations into runtime assertions, and Java PathFinder (JPF) for systematic state-space exploration. Both tools have been verified to work correctly on toy examples.

**Dataset**: We surveyed existing benchmarks for self-contained Java methods suitable for JML evaluation. The most promising sources are SpecGenBench (120 programs with ground-truth JML annotations). An initial candidate pool has been compiled and is being filtered for JPF compatibility.

**Prompting**: We drafted an initial prompt template and tested three prompting strategies — zero-shot, few-shot, and chain-of-thought — on a small number of pilot methods. LLM outputs are generally syntactically valid JML, though postconditions tend to be underspecified.

**Pipeline PoC**: We validated the end-to-end pipeline on two simple methods, confirming the approach is technically feasible.

## Key Risks

- JPF has limited support for certain Java features (reflection, complex libraries), which constrains method selection.
- State-space explosion may require bounding method complexity or configuring JPF search limits.

## Next Steps

- Finalize the curated method set from SV-COMP / SpecGenBench.
- Run LLM generation across all prompting strategies on the full set.
- Build automation scripts for batch pipeline execution.
- Define evaluation metrics and begin systematic analysis of results.
