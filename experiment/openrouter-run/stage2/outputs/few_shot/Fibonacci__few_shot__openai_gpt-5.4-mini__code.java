public class Fibonacci {

	//@ requires n < 0 || n == 0 || n > 0;
	//@ ensures n < 0 ==> \result == -1;
	//@ ensures n == 0 ==> \result == 0;
	//@ ensures n > 0 ==> \result == fibCompute(n);
	public static int fibCompute(int n) {
		if(n < 0) return -1;
		else if(n == 0) return 0;

		int[] fib = new int[n + 1];
		fib[0] = 0;
		fib[1] = 1;
		int index = 2;

		//@ maintaining 2 <= index && index <= fib.length;
		//@ maintaining fib[0] == 0;
		//@ maintaining fib[1] == 1;
		//@ maintaining (\forall int i; 2 <= i && i < index; fib[i] == fib[i - 2] + fib[i - 1]);
		//@ decreases fib.length - index;
		while (index < fib.length) {
			fib[index] = fib[index - 2] + fib[index - 1];
			index++;         
		}

		return fib[n];
	}

}
