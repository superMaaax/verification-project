# Pilot Method Set

## Selected Methods

1. `Absolute`
   Source: `benchmarks/Absolute.java`
   Why: no loop, simple branch behavior, good warm-up example.

2. `AddLoop`
   Source: `benchmarks/AddLoop.java`
   Why: arithmetic preconditions plus simple loop invariants.

3. `FindFirstZero`
   Source: `benchmarks/FindFirstZero.java`
   Why: array search with quantified postconditions and one loop.

4. `LinearSearch`
   Source: `benchmarks/LinearSearch.java`
   Why: array search plus mutable static field and loop invariant.

5. `Smallest`
   Source: `benchmarks/Smallest.java`
   Why: array minimum index task with loop invariant and quantified ensures.

## Why These Five

- They are small enough for hand inspection.
- They cover both branch-only and loop-heavy methods.
- They include arithmetic and array reasoning.
- They avoid very large object/state structure examples for the pilot.

## Deferred For Later

These are useful later, but are intentionally not in the first pilot:

- `GCD`
- `TransposeMatrix`
- `Fibonacci`

They are more demanding and better suited once the prompting and evaluation
workflow is stable.
