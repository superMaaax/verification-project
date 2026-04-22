class RotateArray with a static method rotate that rotates an array to the right by k positions. We need to add JML comments: requires and ensures clauses for each method where appropriate, loop specifications for each loop (maintaining and decreases). Also, add spec_public only when needed for specification visibility. We must not change executable Java code. Output the full Java file with JML annotations.

We need to consider the method signature: public static void rotate(int[] nums, int k). JML requires preconditions (requires) and postconditions (ensures). Also, we need to specify that the method rotates the array. The method uses a local array newArr, then copies back. The loops: for (int i = 0; i < n; ++i). We need to add maintaining and decreases for the loop. Also, we might need to specify that k may be larger than n, but typical rotation uses k % n. However, we can just specify behavior: after rotate, the array elements are rearranged such that each element at index i moves to index (i + k) % n. We can specify that the method permutes the array in a certain way. Also, we need to consider that the method may be called with null array; we can require non-null. Also, k may be negative? Usually k is non-negative. We can require k >= 0. But we can also allow any integer, but rotation is defined modulo n. However, typical specification: ensures that for all i, nums[i] == \old(nums[(i - k) % n]) (or something). Let's define precisely.

We need to add JML comments. The code is short. We'll need to add spec_public maybe for fields? There are no fields. The method uses local variables; we may need to add spec_public for them if needed for specification? Usually spec_public is used to expose private fields to specifications. Not needed here. But we might need to add spec_public for the array? The array is passed as argument; we can refer to it directly. The spec_public may be needed for the local array newArr? But it's local, not visible outside. However, we might need to specify its contents in ensures. But we can just specify the final effect on nums. So spec_public may not be needed.

We need to add JML comments before the class and before the method. Usually JML comments are like //@ or /*@ */. For multi-line, we can use /*@ ... @*/. We'll produce a file with JML comments inserted.

We need to ensure we include the original code unchanged. So we will output the file with JML comments inserted but the code lines unchanged.

We need to consider the loops: The for loop is used to fill newArr. We need to add maintaining and decreases. The loop variable i goes from 0 to n-1. The loop condition is i < n. The loop increments i. So we can add a loop specification: //@ maintaining ... //@ decreases i. But we need to embed within the loop? Usually JML spec for loops are placed as comments before the loop. But we can also embed as //@ maintaining ... etc. The guidelines: For each loop, we need to add maintaining and decreases. So we can add a comment before the for loop: /*@ maintaining ... @*/ and /*@ decreases ... @*/. Or we can combine in one comment.

We also need to add requires and ensures for the method. The requires clause: //@ requires nums != null; //@ requires k >= 0; maybe also require that k < something? But we can allow any integer, but rotation works with modulo. However, typical JML spec would require k >= 0. But we can also require that k <= Integer.MAX_VALUE? Not needed. We'll just require nums != null and k >= 0. Or we could also require k >= 0 && k <= Integer.MAX_VALUE. But we can keep simple.

Ensures: after rotate, the array is rotated right by k positions. We can specify: //@ ensures \forall int i; 0 <= i < nums.length; nums[i] == \old(nums[(i - k) % nums.length]); But careful: modulo with negative? Since k >= 0, it's fine. But we need to handle modulo for negative? Not needed. However, we need to ensure that the modulo operation yields correct index. In Java, % can be negative for negative numbers, but k is non-negative, so (i - k) % n may be negative if i < k. So we need to adjust. But typical rotation definition: newArr[(i + k) % n] = nums[i]; after copy, nums[j] = newArr[j] = old nums[(j - k + n) % n]? Let's derive: original array oldA. After rotation right by k, element at index i in old becomes index (i + k) % n in new. So new[j] = old[(j - k + n) % n]. So after rotation, nums[j] = old[(j - k + n) % n]. So ensures can be: nums[i] == \old(nums[(i - k + nums.length) % nums.length]) for all i. But we can also specify using \old. However, JML requires that \old refers to the value at method entry. So we can use \old(nums)[...]. But we need to be careful: \old is only allowed in ensures and also for assignable etc. We can use \old(nums) to refer to the original array. But we need to ensure we refer to the same array length. So we can write: //@ ensures \forall int i; 0 <= i < nums.length; nums[i] == \old(nums)[(i - k + nums.length) % nums.length]; However, JML expects that the length of nums is unchanged. So we can also ensure that nums.length == \old(nums.length). But that's implied.

Better to write: //@ ensures \forall int i; 0 <= i < nums.length; nums[i] == \old(nums)[(i - k) % nums.length]; But (i - k) may be negative, so we need to add nums.length to adjust: (i - k + nums.length) % nums.length. But JML does not have built-in modulo for negative? It does have % operator, but for negative numbers the result sign follows Java's remainder sign. But we can use (i - k + nums.length) % nums.length. However, we need to ensure that k may be larger than n, but we can also use k % n in spec? But we can just rely on the method's implementation: it uses (i + k) % n. So we can specify accordingly.

Better to specify that after rotate, the array is permuted such that each element moves to the right by k positions modulo length. So we can write ensures: \forall int i; 0 <= i < n; nums[(i + k) % n] == \old(nums)[i]; This is easier: after rotation, the element originally at index i ends up at index (i + k) % n. So we can specify that. But we need to ensure that we refer to the new array after rotation. However, ensures clause describes the state after method execution. So we can say: //@ ensures \forall int i; 0 <= i < nums.length; nums[(i + k) % nums.length] == \old(nums)[i]; This is correct. But we also need to ensure that the array length is unchanged. But we can also ensure that the array is permuted: the set of elements is same as before. But we can just use that condition.

We also need to consider that the method modifies the array in place. The ensures clause should reflect that. Also, we may want to specify that the method does not return a value, but modifies the argument.

We also need to consider that the method may be called with k > n. The method uses (i + k) % n, which handles that. So spec can allow any integer k. But we can restrict k >= 0. We'll do that.

Now loop specifications: For the for loop, we need to add maintaining and decreases. The loop iterates i from 0 to n-1. The loop invariant should describe what has been filled in newArr so far. Typically we can maintain that for all indices j < i, newArr[(j + k) % n] = nums[j]. Or something. But we can also maintain that newArr is being filled correctly. However, we may not need to specify a loop invariant for the method's behavior, but we need to add JML spec for the loop. The guidelines: "Generate loop specifications such as maintaining and decreases for each loop where appropriate." So we need to add a loop invariant and a decreases clause. Usually we add //@ maintaining <invariant> and //@ decreases <variant>. The invariant should be something that holds at each iteration. The decreases clause should be something that decreases each iteration and bounds the loop.

We can write something like:

/ *@
  @ maintaining 0 <= i && i <= n;
  @ maintaining (\forall int j; 0 <= j < i; newArr[(j + k) % n] == nums[j]);
  @ decreases n - i;
  @*/

But we need to embed these as JML comments. Usually we can put them before the for loop as a multi-line comment:

/*@ maintaining 0 <= i && i <= n; @*/
/*@ maintaining (\forall int j; 0 <= j < i; newArr[(j + k) % n] == nums[j]); @*/
/*@ decreases n - i; @*/

But we can combine them in one comment block:

/*@
  @ maintaining 0 <= i && i <= n;
  @ maintaining (\forall int j; 0 <= j < i; newArr[(j + k) % n] == nums[j]);
  @ decreases n - i;
  @*/

But we need to ensure that the comment is placed before the for loop. However, we need to be careful: JML comments are processed by the JML compiler, but we can embed them as //@ or /*@ ... @*/. Usually we can use //@ for single line, and /*@ ... @*/ for multi-line. The spec says "Add JML directly into the given Java source code." So we can insert JML comments in the code. The code lines themselves must remain unchanged. So we need to insert JML comments before the code lines, but not modify the code lines. For example, we can add a comment line before the method, and before the loop. But we must not change the code lines (like adding code). So we can add comment lines in between.

We need to output the full Java file with JML comments inserted. The original code is:

class RotateArray {
    public static void rotate(int[] nums, int k) {
        int n = nums.length;
        int[] newArr = new int[n];
        for (int i = 0; i < n; ++i) {
            newArr[(i + k) % n] = nums[i];
        }
        System.arraycopy(newArr, 0, nums, 0, n);
    }
}

We need to add JML comments before the class and before the method and before the loop. The spec says "Add JML directly into the given Java source code." So we can add JML comments as //@ or /*@ ... @*/. Usually JML comments are placed before the code they annotate. For the method, we can add requires and ensures after the method signature but before the body. In JML, we can write:

/*@
  @ requires nums != null;
  @ requires k >= 0;
  @ ensures \forall int i; 0 <= i < nums.length; nums[(i + k) % nums.length] == \old(nums)[i];
  @*/

public static void rotate(int[] nums, int k) {
    // method body
}

But we need to ensure that the comment is placed before the method signature? Usually we place the JML spec before the method, like:

/*@
  @ requires nums != null;
  @ requires k >= 0;
  @ ensures \forall int i; 0 <= i < nums.length; nums[(i + k) % nums.length] == \old(nums)[i];
  @*/
public static void rotate(int[] nums, int k) {

But we need to ensure that the comment does not interfere with the code. The spec says "Do not change executable Java code." So we can add JML comments as separate lines, but we cannot modify the existing lines. So we can add comment lines before the method, but we cannot insert text within the method's code line (like after the signature) because that would be adding code? Actually adding a comment is not changing executable code, but we need to be careful: The JML comment is not part of the executable code, but it's a comment. So we can add it. However, we need to ensure that we do not break the syntax. For example, we can add a JML comment before the method, but we need to ensure that the comment is placed in a location that is allowed for JML. Usually JML comments can be placed before class declarations, method declarations, etc. They can also be placed inside method bodies for loop specifications. So we can add them.

We also need to add loop specifications for the for loop. We can add them before the for statement. For example:

/*@
  @ maintaining 0 <= i && i <= n;
  @ maintaining (\forall int j; 0 <= j < i; newArr[(j + k) % n] == nums[j]);
  @ decreases n - i;
  @*/
for (int i = 0; i < n; ++i) {

But we need to ensure that the comment does not break the code. The for loop line is a statement, we can add a comment line before it. That is fine.

We also need to add spec_public only when needed for specification visibility. In this code, there are no fields. The local variables newArr and n are local to the method. In JML, we may need to specify them as spec_public if we want to refer to them in specifications. But we can refer to them directly in the loop spec. However, JML spec for loops may need to refer to local variables. Usually we can refer to them in the spec. But we need to ensure that they are visible. In JML, local variables are not automatically visible in spec. But we can declare them as spec_public? Actually spec_public is used for fields, not local variables. For local variables, we can just refer to them in spec if they are in scope. However, JML spec for loops may need to refer to the loop variable and other local variables. Usually we can just use them. But we need to be careful: The JML spec for loops is placed inside the method, and we can refer to local variables. However, we may need to add a declaration like //@ public int n; but that would be adding code. But we can just refer to them. The spec_public is only needed for fields that are private but need to be visible in spec. Since there are no fields, we don't need spec_public.

Thus we can just add the loop spec without spec_public.

Now we also need to consider the ensures clause for the method. We need to ensure that the array is rotated correctly. The ensures clause we wrote uses \old(nums). However, we need to be careful: In JML, \old(expr) refers to the value of expr at the beginning of the method. So we can use \old(nums). But we need to ensure that we refer to the array's length correctly. The spec we wrote uses \old(nums)[i] and nums[(i + k) % nums.length]. However, we need to ensure that the length is unchanged. But we can also ensure that the array length is unchanged by requiring that the length remains same. But we can just assume that.

We also need to ensure that the ensures clause is correct for all i. However, we need to be careful about the modulo operation for negative numbers. Since k >= 0, (i + k) % n is always non-negative. So it's fine. But we need to ensure that the ensures clause uses the same length as the original array. The expression \old(nums)[i] will have the same length as nums. So it's fine.

We also need to ensure that the ensures clause does not refer to the new array variable newArr, which is local. We can just refer to nums.

Thus we can write ensures as:

ensures \forall int i; 0 <= i < nums.length; nums[(i + k) % nums.length] == \old(nums)[i];

But we also need to ensure that the array is permuted. However, we also need to ensure that the method does not change the length. But we can also add ensures that nums.length == \old(nums.length). But it's not necessary.

We also need to consider that the method may be called with k > n. The ensures clause works for any integer k because we use modulo. However, we need to ensure that the ensures clause uses modulo correctly. In JML, the modulo operator % works similarly to Java. But we need to ensure that we use parentheses correctly. The expression (i + k) % nums.length is fine.

Now we also need to add a requires clause for k maybe we can require k >= 0. But we can also allow any integer. But typical spec for rotation expects k >= 0. We'll add requires k >= 0.

We also need to add a requires clause for nums != null. We'll add that.

Now we also need to consider that the method may be called with k negative. If k is negative, the rotation direction may be opposite. But we can just require k >= 0. The problem statement does not specify, but typical rotation uses non-negative k. We'll add requires k >= 0.

Now we also need to consider that the method may be called with k > Integer.MAX_VALUE, but we can ignore.

Now we also need to add a decreases clause for the loop. The loop variable i increases from 0 to n. The decreases clause should be something that decreases each iteration and is bounded below. Usually we can use n - i, which decreases from n to 0. Or we can use n - i, but we need to ensure that it's a non-negative integer expression. Since i increases, n - i decreases. So we can use decreases n - i. However, we need to ensure that the decreases clause is a integer expression that gets smaller each iteration and is bounded below by 0. So we can use decreases n - i. But we need to ensure that we refer to i, which is the loop variable. However, we need to be careful: The decreases clause is used for loop variant, which is a measure of progress. Usually we can use decreases i; but i increases, so that wouldn't be correct. We need something that decreases. So we can use decreases n - i. But we need to ensure that the expression is integer and decreases. Since i increases, n - i decreases. So it's okay.

Alternatively, we can use decreases n - i, but we need to ensure that the bound is non-negative. Since
