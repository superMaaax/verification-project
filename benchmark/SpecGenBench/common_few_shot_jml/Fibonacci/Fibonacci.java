public class Fibonacci {

	//@ requires true;
	//@ ensures n < 0 ==> \result == -1;
	//@ ensures n == 0 ==> \result == 0;
	//@ ensures n == 1 ==> \result == 1;
	//@ ensures n >= 2 ==>
	//@   (\exists int[] a;
	//@        a != null && a.length == n + 1 &&
	//@        a[0] == 0 && a[1] == 1 &&
	//@        (\forall int j; 2 <= j && j <= n; a[j] == a[j - 2] + a[j - 1]) &&
	//@        \result == a[n]);
	public static int fibCompute(int n) {
		if(n < 0) return -1;
		else if(n == 0) return 0;

		int[] fib = new int[n + 1];
		fib[0] = 0;
		fib[1] = 1;
		int index = 2;

		//@ maintaining 2 <= index && index <= fib.length;
		//@ maintaining fib[0] == 0 && fib[1] == 1;
		//@ maintaining (\forall int j; 2 <= j && j < index; fib[j] == fib[j - 2] + fib[j - 1]);
		//@ decreases fib.length - index;
		while (index < fib.length) {
			fib[index] = fib[index - 2] + fib[index - 1];
			index++;         
		}

		return fib[n];
	}

}
