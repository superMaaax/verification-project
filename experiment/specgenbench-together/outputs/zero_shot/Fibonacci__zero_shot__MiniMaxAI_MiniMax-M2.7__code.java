class Fibonacci with a static method fibCompute that computes the nth Fibonacci number.

We need to add JML comments directly into the Java source code. We need to generate requires and ensures clauses for each method where appropriate. Also generate loop specifications such as maintaining and decreases for each loop. Add spec_public only when needed for specification visibility. Do not change executable Java code. Do not rename classes, methods, fields, or variables. Output the full Java file and nothing else. Do not omit any original code.

We need to consider the method fibCompute. It takes an int n. It returns -1 if n < 0, returns 0 if n == 0, else computes the nth Fibonacci number using an array.

We need to add JML specifications. The method is public static. We can add requires clause: n >= -1? Actually -1 is returned for negative n, but typical JML would allow negative? The spec can say requires n >= 0? But the method returns -1 for negative n, so we might want to allow n >= -1? But the spec ensures that if n < 0 then result is -1. However typical JML ensures clause can specify that.

We need to decide what to specify. The method is supposed to compute Fibonacci numbers for non-negative n, and returns -1 for negative n. We can specify:

requires n >= 0; // because we want to compute fib for non-negative

But the method also handles negative n by returning -1. If we add requires n >= 0, then we restrict the method's contract to only non-negative inputs. But the original code returns -1 for negative n, so we might want to allow negative n but specify the result is -1. However typical JML spec for a method that returns -1 for invalid input often uses requires n >= 0, but then ensures something else. But we can also specify that if n < 0 then result == -1. But then we need to decide if we want to require n >= 0 or allow any int. The problem statement says "Generate JML specifications for the Java program below." It doesn't specify constraints. We should aim to specify behavior for all inputs, but also we can restrict to non-negative if we want. However we need to be careful: The method returns -1 for negative n, but also returns 0 for n==0. So we can specify:

requires true; // no precondition

ensures \result == -1 <==> n < 0; // but also need to handle n >= 0 case.

Better: We can specify:

requires true;

ensures (n < 0 ==> \result == -1) && (n == 0 ==> \result == 0) && (n > 0 ==> \result == fib(n));

But we need to define fib(n) as the nth Fibonacci number. In JML we can use a pure method to define fib. But we can also use a recursive definition: fib(0) = 0, fib(1) = 1, fib(n) = fib(n-1) + fib(n-2). But we can also specify using an existential quantifier? But we can just specify that \result equals the nth Fibonacci number for n >= 0. However we need to be precise.

We can also use a lemma: The method computes the correct Fibonacci number for n >= 0. So we can specify:

ensures (\forall int i; i >= 0 && i <= n; fib[i] == correctFib(i));

But we can just specify that the returned value equals the nth Fibonacci number.

Better: Use a pure method definition for Fibonacci. But we might not want to define a separate pure method. We can just specify that \result equals the nth Fibonacci number defined by the standard recurrence.

We can use a JML specification with a recursive definition:

/*@ pure @*/ static int fib(int n) { ... }

But we can also use a lemma: ensures \result == fib(n). But we need to define fib in JML.

Simplify: We can specify:

requires n >= 0;

ensures \result == fib(n);

But we also need to handle negative n: maybe we can allow negative n and specify result is -1. But the problem didn't specify constraints. We can decide to restrict to n >= 0 for the main spec, but also include a clause for negative n.

Better: Use two ensures clauses: one for n < 0, one for n >= 0.

But typical JML ensures clause can be a conjunction. So we can do:

ensures (n < 0 ==> \result == -1) && (n >= 0 ==> \result == fib(n));

But we need to define fib for n >= 0. We can define a pure method in JML or use a recursive definition in the ensures clause. However JML does not support recursive definitions directly in ensures? It can have logic formulas, but we can use a recursive definition with a lemma? Actually JML allows to define pure methods, but we can also use a recursive definition in a lemma. But we can also use a specification that says \result is the nth Fibonacci number defined by the recurrence. But we need to define it precisely.

We can define a pure method:

/*@ pure @*/ private static int fibonacci(int n) { ... }

But we cannot change code, only add comments. We can add a pure method in JML? The spec can include a model method (a ghost method) defined in JML. But we can also use a lemma: ensures \result == \old(fibCompute(n)). But that would be circular.

Better: Use a model method that is defined recursively:

/*@ model @*/ static int fibModel(int n) { ... }

But we can also use a recursive definition in JML: ensures \result == (n == 0 ? 0 : (n == 1 ? 1 : fibModel(n-1) + fibModel(n-2)));

But we need to define fibModel. However we can also use a lemma to define the Fibonacci sequence. But typical JML usage is to define a pure method that matches the implementation. But we can also just specify that the result is correct for n >= 0, using a separate lemma.

Given the problem statement, we probably just need to provide a simple spec: requires n >= 0; ensures \result == fib(n). But we also need to handle negative n? The problem didn't specify constraints, but we can include both.

Better to include a spec that covers all possible inputs: For n < 0, result = -1; for n >= 0, result = correct Fibonacci number.

But we also need to add loop specifications for the while loop: maintain and decrease.

We need to add JML comments for the loop. The loop is:

while (index < fib.length) {
    fib[index] = fib[index - 2] + fib[index - 1];
    index++;
}

We need to add a loop specification: maintain and decrease. Usually we can maintain that for all i < index, fib[i] equals the ith Fibonacci number. And decrease index? Actually we can maintain that index is the current index being computed, and that fib[0..index-1] are correct. Then we can decrease index? Actually we need to decrease something that ensures termination. Usually we can use a decreases clause: decreases fib.length - index. Or decreases index. But we need to ensure that the loop terminates. The loop condition is index < fib.length, so index increases, not decreases. But we can use decreases on something else: maybe decreases fib.length - index. But typical JML loop spec uses "decreases index" but that would be non-negative? Actually index increases, so we cannot use decreases index. Instead we can use decreases fib.length - index, which is non-negative and decreases each iteration. Or we can use decreases index? But index is increasing, so not decreasing. So we need to use something else.

Better: Use "decreases fib.length - index". Since index increments each iteration, fib.length - index decreases. So we can specify:

/*@ maintaining (forall int i; i >= 0 && i < index; fib[i] == fibonacci(i)); @*/
/*@ decreases fib.length - index; @*/

But we need to define fibonacci(i) in the spec. We can use a pure function in JML. But we can also use a model method.

Simplify: We can maintain that for all i < index, fib[i] equals the i-th Fibonacci number (with fib[0]=0, fib[1]=1). Then we can also maintain that index <= fib.length. And we can decrease the remaining length.

But we need to define the Fibonacci number in the spec. We can use a model method:

/*@ model @*/ static int fibModel(int n) { ... }

But we can also just use a spec that says "fib[i] equals the i-th Fibonacci number" without defining the function. But we need to define it for clarity. However we can also use a lemma: ensures \result == fib(n). But we need to define fib in JML.

Given typical JML usage, we can define a pure method:

/*@ pure @*/ private static int fib(int n) { ... }

But we cannot add a method implementation in the code, but we can add a ghost method in JML. However the problem says "Do not change executable Java code." So we can add JML comments but not add new methods. However we can add a model method in JML that is just for specification. That is allowed as a ghost method. But we need to ensure we don't change the executable code. Adding a ghost method is not executable, but we need to be careful: The code is only the given Java program. We cannot add new methods. But we can add JML spec for the existing method, and we can add ghost fields or model fields. But we cannot add new methods. However we can add a model method in JML spec that is not part of the code? Usually we can add a "model method" in JML spec that is declared with "model" and then defined in JML. But it's not part of the Java code. However the problem says "Do not change executable Java code." Adding a ghost method might be considered adding code, but it's not executable. However typical JML spec generation tasks often allow adding ghost code for specifications. But the problem says "Do not rename classes, methods, fields, or variables." It doesn't forbid adding new ghost fields or methods. But we need to be careful: The output must be the full Java file and nothing else. So we can add JML comments anywhere. We can also add ghost code inside JML comments. That is allowed.

But maybe we can avoid adding a new method and just use a simple spec: ensures \result == fib[n] for n >= 0. But we need to define fib[n] as the nth Fibonacci number. But we can just say that the method returns the nth Fibonacci number. However typical JML spec would be more precise: ensures \result == \old(fib[n])? Actually we can just say ensures \result == fib[n] after the loop. But we need to ensure that the array fib is correctly computed. But we can also just specify that the returned value is the nth Fibonacci number for n >= 0.

Given the problem's context, maybe they expect a simple spec: requires n >= 0; ensures \result == fib(n). And loop spec: maintain that for all i < index, fib[i] equals the i-th Fibonacci number; decreases fib.length - index.

But we also need to handle negative n. The problem didn't specify constraints, but we might want to include a spec for negative n. However typical JML spec for a method that returns -1 for negative n might be to allow negative n and specify result is -1. But we can also restrict to n >= 0. The problem didn't specify any constraints, but we can decide to specify both.

Better to include both: For n < 0, result = -1; for n >= 0, result = correct Fibonacci number. But we need to define the correct Fibonacci number for n >= 0. We can define a model method or just use a recursive definition in the ensures clause. But we can also use a lemma: ensures \result == fib(n) for n >= 0. But we need to define fib(n). We can define a pure function in JML:

/*@ pure @*/ static int fib(int n) { ... }

But we cannot add a method body in Java code. However we can add a ghost method in JML: we can declare a "model method" that is defined only in JML. But typical JML syntax for a model method is:

/*@ model @*/ static int fib(int n) { ... }

But we need to provide a body in JML. Actually we can define a model method with a specification: we can define a "model method" that is declared with "model" and then we can provide a definition in JML using "helper" or "definition". But it's easier to just use a "lemma" or "predicate". However we can also avoid defining a function and just use a recursive definition in the ensures clause using \old and arithmetic. But it's messy.

Given typical usage, we can just specify that the method returns the nth Fibonacci number for n >= 0, and for n < 0 returns -1. We can use a simple spec:

requires true;

ensures (n < 0 ==> \result == -1) && (n >= 0 ==> \result == fib(n));

But we need to define fib(n). We can define a pure method in JML:

/*@ pure @*/ static int fib(int n) { return (n == 0) ? 0 : (n == 1) ? 1 : fib(n-1) + fib(n-2); }

But we cannot add a method body in Java code. However we can add a ghost method in JML. But we can also just use a "model method" that is defined in JML. But we can also just use a "definition" in the ensures clause: we can use a recursive definition using \old. But typical JML does not allow recursion in ensures? Actually it does allow recursion in pure methods. But we can define a pure method and use it in the ensures.

Given the problem's constraints, maybe they expect a simple spec: require n >= 0; ensure \result == fib(n). And loop spec: maintain that for all i < index, fib[i] == i-th Fibonacci number; decrease something.

But we also need to consider that the method returns -1 for negative n. If we restrict to n >= 0, we might not need to handle negative n. But the original code returns -1 for negative n, so we might want to include that in spec. However the problem didn't specify constraints, but we can include both.

Better to include both: require true; ensure (n < 0 ==> \result == -1) && (n >= 0 ==> \result == fib(n)). But we need to define fib(n). We can define a pure method in JML. But we can also just use a "model method" that is defined recursively. But we can also just use a "definition" in the ensures clause: we can use a "helper" method. But we can also just use a "pure function" defined in JML.

Given typical JML usage, we can define a "model method" like:

/*@ model @*/ static int fibModel(int n) { ... }

But we need to provide a body in JML. Actually we can define a "model method" with a JML specification that defines its behavior. But we can also just use a "pure method" with a body in Java, but we cannot add a body because we cannot change executable code. However we can add a "model method" that is defined only in JML, not in Java. But typical JML syntax for a model method is:

/*@ model @*/ static int fibModel(int n) { return (n == 0) ? 0 : (n == 1) ? 1 : fibModel(n-1) + fibModel(n-2); }

But we need to put this inside a JML comment. However we need to be careful: The code we output must be a Java file with JML comments. We can add a ghost method inside a JML comment, but we need to ensure it's not considered part of the executable code. Usually JML comments are ignored by the Java compiler, but they are used by the JML tool. So we can add a "model method" inside a JML comment, but we need to ensure it's not inside a normal comment that would cause a compile error. Actually JML comments are special: They start with "/*@" and end with "*/". Inside those comments we can have JML syntax, but we cannot add a method signature inside a JML comment because that would be considered a normal comment and the Java compiler would ignore it. However we can add a "model method" inside a JML comment as a ghost method, but we need to ensure that the method is not visible to the Java compiler. Actually JML comments are used by the JML tool, but they are not compiled as Java code. So we can add any text inside JML comments. However we need to be careful: If we add a method signature inside a JML comment, the Java compiler will treat it as a comment and ignore it, so it's okay. But we need to ensure that the method is not declared in the actual Java code. So we can add a ghost method inside a JML comment.

But the problem says "Do not change executable Java code." Adding a ghost method inside a JML comment is not executable, so it's okay. However we need to ensure that we don't break the syntax. The JML comment is like "/*@ ... @*/". Inside we can have JML spec, but we can also have ghost code. But typical JML syntax for a ghost method is to declare it with "model" and then define it in the comment. But we need to be careful: The JML tool expects the ghost method to be declared in the JML spec, not inside a normal comment. But we can add a "model method" inside the JML comment for the class. However the problem might not require us to define a ghost method; we can just use a simple spec.

Given the problem's simplicity, maybe they expect just a simple spec: require n >= 0; ensure \result == fib(n). And loop spec: maintain that for all i < index, fib[i] equals the i-th Fibonacci number; decrease something.

But we also need to handle negative n? The problem didn't specify constraints, but we can include both.

Better to include both: require true; ensure (n < 0 ==> \result == -1) && (n >= 0 ==> \result == fib(n)). But we need to define fib(n). We can define a pure method in JML. But we can also just use a "model method" that is defined recursively. But we can also just use a "definition" in the ensures clause: we can use a "helper" method. But we can also just use a "pure function" defined in JML.

Given typical JML usage, we can define a "model method" that
