public class Fibonacci {

	/*@ spec_public @*/
	public static /*@ pure @*/ int fibCompute(int n) 
		/*@ 
			requires n >= 0;
			ensures \result == fib(n);
		@*/
	{
		if(n < 0) return -1;
		else if(n == 0) return 0;

		int[] fib = new int[n + 1];
		fib[0] = 0;
		fib[1] = 1;
		int index = 2;

		/*@ 
			loop_invariant 
				2 <= index && index <= n + 1 &&
				(\forall int k; 0 <= k && k < index; 
					fib[k] == k == 0 ? 0 : k == 1 ? 1 : fib[k-2] + fib[k-1]) &&
				fib[0] == 0 && fib[1] == 1;
			decreases n + 1 - index;
		@*/
		while (index < fib.length) {
			fib[index] = fib[index - 2] + fib[index - 1];
			index++;         
		}

		return fib[n];
	}

}
