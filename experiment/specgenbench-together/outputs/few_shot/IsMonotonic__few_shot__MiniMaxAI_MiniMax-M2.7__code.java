class IsMonotonic {
    public boolean isMonotonic(int[] nums) {
        boolean inc = true, dec = true;
        int n = nums.length;
        for (int i = 0; i < n - 1; ++i) {
            if (nums[i] > nums[i + 1]) {
                inc = false;
            }
            if (nums[i] < nums[i + 1]) {
                dec = false;
            }
        }
        return inc || dec;
    }
}

We need to add JML comments: requires and ensures clauses for the method, and loop specifications for the for loop (maintaining, decreases). Also spec_public if needed for fields that are used in spec but not visible. In this case, we have no fields, only local variables. The method is public, so we need to specify its behavior.

The method checks if array is monotonic increasing, decreasing, or both. The result is true if array is either non-decreasing (inc) or non-increasing (dec). Actually inc means "still possibly non-decreasing" after scanning; dec means "still possibly non-increasing". At the end, inc || dec indicates monotonic.

We need to produce JML spec: requires: array not null? Typically JML requires non-null for arrays. We'll add //@ requires nums != null; Possibly also require n >= 0 but always true. Ensure: \result == true <==> the array is monotonic (i.e., either non-decreasing or non-increasing). Or we can express as ensures: \result == (\forall int i; 0 <= i && i < nums.length - 1; nums[i] <= nums[i+1]) || (\forall int i; 0 <= i && i < nums.length - 1; nums[i] >= nums[i+1]). Or we can express as ensures: \result == (isNonDecreasing || isNonIncreasing). But we can use quantifiers.

Better: //@ ensures \result == ((\forall int i; 0 <= i && i < nums.length - 1; nums[i] <= nums[i+1]) || (\forall int i; 0 <= i && i < nums.length - 1; nums[i] >= nums[i+1])); This expresses monotonic.

Alternatively, we can break into ensures for inc and dec but simpler to combine.

Loop specification: for loop iterates i from 0 to n-2. We need maintaining: after each iteration, inc indicates that no increase has been seen so far (i.e., all seen pairs satisfy nums[j] <= nums[j+1] for j < i). dec indicates that no decrease has been seen so far (i.e., all seen pairs satisfy nums[j] >= nums[j+1] for j < i). So we can maintain:

//@ maintaining 0 <= i && i <= n-1; // i is loop index variable, but in JML we refer to the variable i. However, we cannot refer to i inside JML unless we declare it as spec_public? Actually we can refer to local variables in JML annotations as long as they are in scope. JML supports referencing local variables. But we need to be careful: The loop variable i is declared in the for loop; we can refer to it in maintaining and decreases clauses. However, JML's maintaining clause is evaluated at each loop iteration; we need to refer to i. But i is not visible outside the loop. In JML, we can use i inside maintaining and decreases because they are inside the loop context. However, we need to ensure that we use the correct syntax: //@ maintaining ...; The variable i is in scope.

But we need to also include decreases clause: //@ decreases n - i; Since i increments each iteration, the loop bound decreases.

We also need to ensure that the loop invariant includes the relationship between inc, dec, and the prefix of the array. Something like:

//@ maintaining inc == (\forall int j; 0 <= j && j <= i; nums[j] <= nums[j+1]); // but note that for j = i, we need to check up to i? Actually inc is set to false if any increase is found. At start i = 0, inc is true. After each iteration, inc becomes false if any pair (nums[i] > nums[i+1]) is seen. So inc indicates that for all j from 0 to i, we have not seen a decreasing pair? Wait inc is true if we haven't seen any decreasing pair? Actually inc is true initially, then if any nums[i] > nums[i+1] (i.e., a decrease), inc is set to false. So inc indicates that the array is still possibly non-decreasing (i.e., no decreasing pair encountered). Similarly, dec is true initially, then if any nums[i] < nums[i+1] (i.e., an increase), dec is set to false. So dec indicates that the array is still possibly non-increasing.

Thus after iteration i (i.e., after processing pair (i, i+1)), inc is true iff all pairs up to i satisfy nums[j] <= nums[j+1] (i.e., no decreasing pair). Similarly dec is true iff all pairs up to i satisfy nums[j] >= nums[j+1] (i.e., no increasing pair). So we can maintain:

//@ maintaining inc == (\forall int j; 0 <= j && j <= i; nums[j] <= nums[j+1]);
//@ maintaining dec == (\forall int j; 0 <= j && j <= i; nums[j] >= nums[j+1]);

But note that for i = n-2 (last iteration), after processing last pair, we have i = n-2. Then inc and dec reflect all pairs. However, we need to be careful about the quantifier range: For j up to i, we need to ensure j+1 < n. Since i <= n-2, j+1 <= n-1, so it's safe. At the start of loop (i = 0), we need to consider the condition before any iteration. At that point, inc and dec are true, and the quantifier over empty set is true. So we can define the invariant accordingly.

Alternatively, we can maintain inc == true && (\forall int j; 0 <= j && j < i; nums[j] <= nums[j+1]) etc. But we can incorporate the condition that inc is true if no decreasing pair encountered. However, inc can become false, but we can maintain that inc is false if any decreasing pair encountered. So we can maintain:

//@ maintaining inc == (\forall int j; 0 <= j && j < i; nums[j] <= nums[j+1]);
//@ maintaining dec == (\forall int j; 0 <= j && j < i; nums[j] >= nums[j+1]);

But note that inc is initially true, and after processing pair (i-1, i) we set inc = false if that pair is decreasing. So after iteration i (i.e., after processing pair (i, i+1)), inc should reflect pairs up to i. So we need to adjust accordingly. Let's define loop invariant at the start of each iteration (i is the current index). At that point, we have processed pairs for indices 0..i-1. So inc is true iff all those pairs are non-decreasing. Similarly dec is true iff all those pairs are non-increasing. So we can maintain:

//@ maintaining inc == (\forall int j; 0 <= j && j < i; nums[j] <= nums[j+1]);
//@ maintaining dec == (\forall int j; 0 <= j && j < i; nums[j] >= nums[j+1]);

At the start of loop, i = 0, so the quantifier range is empty, which is true, and inc and dec are true. Good.

Then after each iteration we update inc/dec based on current pair (i, i+1). So after iteration, i increments. At the next iteration, the invariant uses i as new index.

Thus we can add loop spec:

//@ maintaining inc == (\forall int j; 0 <= j && j < i; nums[j] <= nums[j+1]);
//@ maintaining dec == (\forall int j; 0 <= j && j < i; nums[j] >= nums[j+1]);
//@ decreases n - i;

But we also need to ensure that i is bounded: //@ maintaining i >= 0 && i <= n-1; Actually i runs from 0 to n-1 (since loop condition i < n-1). At each iteration, i increments. So we can also maintain i <= n-1. But we can also include that.

Better to include:

//@ maintaining 0 <= i && i <= n-1;

But we also need to ensure that i is integer and n is defined. Since n is local variable, we can refer to it. However, we need to ensure that n is visible in JML spec. Usually JML can refer to local variables. But we need to be careful: In JML, we can refer to local variables of the method in spec, but they must be in scope. Yes, we can.

Thus we can add:

//@ maintaining 0 <= i && i <= n-1;

But we also need to ensure that i is not negative. The loop starts at i = 0, condition i < n-1 ensures i <= n-2. So we can maintain i <= n-1. But we can also maintain i <= n-1. However, we also need to ensure that i is incremented each iteration.

We also need to ensure that the loop terminates: decreases clause ensures termination. The bound is n - i, which starts at n (i=0) and decreases to 1 (when i = n-1). Actually when i = n-1, loop condition fails, but we can have bound n - i which is non-negative and decreases each iteration. At i = n-1, bound is 1? Actually if n = length, then i runs up to n-2. At i = n-2, bound = n - (n-2) = 2. At next iteration i increments to n-1, loop condition fails, but we don't need to consider bound after loop. So we can set decreases n - i - 1? But typical pattern: //@ decreases n - i; works because when i = n-1, bound is negative? Actually we need to ensure bound is non-negative for each iteration. At start i = 0, bound = n. At each iteration i increments, bound decreases. At last iteration before loop exit, i = n-2, bound = 2. After iteration i increments to n-1, loop condition fails, but we don't need to check bound after loop. So we can use decreases n - i. However, JML expects decreases expression to be non-negative for each iteration. At i = n-1 (which is after loop exit) we won't have a iteration, but we can still have bound negative? Actually we need to ensure that the decreases clause is evaluated at each iteration, not after loop. So it's fine.

But we also need to ensure that we include spec_public for any fields that are not visible but needed for spec. There are no fields.

Thus final code:

class IsMonotonic {
    //@ requires nums != null;
    //@ ensures \result == ((\forall int i; 0 <= i && i < nums.length - 1; nums[i] <= nums[i+1]) || (\forall int i; 0 <= i && i < nums.length - 1; nums[i] >= nums[i+1]));
    public boolean isMonotonic(int[] nums) {
        boolean inc = true, dec = true;
        int n = nums.length;
        //@ maintaining 0 <= i && i <= n-1;
        //@ maintaining inc == (\forall int j; 0 <= j && j < i; nums[j] <= nums[j+1]);
        //@ maintaining dec == (\forall int j; 0 <= j && j < i; nums[j] >= nums[j+1]);
        //@ decreases n - i;
        for (int i = 0; i < n - 1; ++i) {
            if (nums[i] > nums[i + 1]) {
                inc = false;
            }
            if (nums[i] < nums[i + 1]) {
                dec = false;
            }
        }
        return inc || dec;
    }
}

But we need to ensure that the loop spec is placed before the for loop. In JML, we can place loop specifications before the loop as comments with //@. The example shows they placed maintaining and decreases before the while loop. For for loops, we can also place them before the for loop. The spec is similar.

We also need to ensure that we include spec_public only when needed. In this case, we have no fields, so not needed.

We also need to ensure that we do not rename anything.

Thus final answer is the annotated Java file.

But we need to check if any other specifications needed: The ensures clause may need to handle empty array case. The original code does not handle empty array specially; if nums.length == 0, then loop condition i < n - 1 becomes i < -1? Actually n = 0, then n - 1 = -1, loop condition i < -1 will be false for i = 0, so loop never runs. Then inc and dec remain true, and method returns true. Is that correct? Typically monotonic for empty array is considered true (vacuously monotonic). But we might want to specify that. However, the original code returns true for empty array. Our ensures clause using quantifiers over range 0 <= i && i < nums.length - 1 will be vacuously true when length is 0 or 1, because the range is empty. So \result will be true. That's consistent.

But we also need to ensure that the ensures clause works for length 1: The quantifier over i from 0 to -1 is empty, so both disjuncts are true, so \result = true. That's correct.

Thus our ensures clause is fine.

We also need to ensure that we include the requires clause for non-null. Usually JML requires that arrays are non-null if we want to refer to length and elements. The original code does not check for null; if null is passed, it will throw NullPointerException. We can add requires nums != null; but we might also want to allow null? Usually we require non-null. We'll add that.

Now we need to ensure that we include the loop invariants correctly. The invariants we wrote refer to i and n. However, we need to ensure that i is visible in the invariants. In JML, we can refer to the loop variable i inside maintaining and decreases. However, we need to ensure that we use the correct syntax for quantifiers: (\forall int i; 0 <= i && i < something; ...). But we also used i inside the quantifier, which shadows the loop variable i? Actually we used i inside the quantifier as a bound variable, but we also refer to i in the maintaining clause as the loop variable. That could cause confusion. In JML, we can use a different variable name for the quantifier to avoid shadowing. In the example they used (\forall int i; 0 <= i && i < index; a[smallest] <= a[i]); They used i as quantifier variable and also used index variable. That's fine because they used different variable names: i for quantifier, index for loop variable. In our case, we have loop variable i and we also want to use i in quantifier. That would cause shadowing. We can rename quantifier variable to something else, like j. So we should use (\forall int j; 0 <= j && j < i; nums[j] <= nums[j+1]). That avoids conflict.

Thus we need to adjust invariants accordingly.

Also we need to ensure that we include the maintaining clause for i bounds. We can include //@ maintaining 0 <= i && i <= n-1; but note that i is loop variable, and we need to refer to i in the clause. That's fine.

But we also need to ensure that we include the decreases clause. The decreases clause should be //@ decreases n - i; but we need to ensure that i is integer and n is integer. That's fine.

Now we need to ensure that we include spec_public only when needed. There are no fields, so none.

Thus final code:

class IsMonotonic {
    //@ requires nums != null;
    //@ ensures \result == ((\forall int i; 0 <= i && i < nums.length - 1; nums[i] <= nums[i+1]) || (\forall int i; 0 <= i && i < nums.length - 1; nums[i] >= nums[i+1]));
    public boolean isMonotonic(int[] nums) {
        boolean inc = true, dec = true;
        int n = nums.length;
        //@ maintaining 0 <= i && i <= n-1;
        //@ maintaining inc == (\forall int j; 0 <= j && j < i; nums[j] <= nums[j+1]);
        //@ maintaining dec == (\forall int j; 0 <= j && j < i; nums[j] >= nums[j+1]);
        //@ decreases n - i;
        for (int i = 0; i < n - 1; ++i) {
            if (nums[i] > nums[i + 1]) {
                inc = false;
            }
            if (nums[i] < nums[i + 1]) {
                dec = false;
            }
        }
        return inc || dec;
    }
}

But we need to check if we need to include spec_public for any variables used in spec that are not visible. The method is public, but we have local variables inc, dec, n, i. They are not fields, but they are used in spec only inside loop invariants and decreases. However, JML can refer to local variables in spec, but they must be in scope. Usually JML can refer to local variables in method specifications, but not in class specifications. However, we are using them inside loop invariants, which are local to the method. That's fine.

But we also need to consider that JML may require that we declare spec_public for fields that are used in spec but are private. In this case, there are no fields.

Thus final answer is as above.

But we need to ensure that we include the JML comments exactly as required: each comment line starts with //@. The example shows they placed them before the method and before loops. We need to ensure we place them correctly.

One nuance: The ensures clause uses \result. That's fine.

We also need to ensure that we include the requires clause for nums != null. However, we might also want to allow null? Usually we require non-null. We'll include that.

Now we need to check if we need to include any other specifications: The method returns boolean. The ensures clause we wrote uses quantifiers to define monotonic property. However, we need to ensure that the ensures clause matches the actual behavior: The method returns inc || dec. At the end, inc is true if no decreasing pair was found; dec is true if no increasing pair was found. So the
