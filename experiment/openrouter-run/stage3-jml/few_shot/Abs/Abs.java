public class Abs {
	
	//@ ensures (num < 0 ==> \result == -num) && (num >= 0 ==> \result == num);
	//@ requires num != Integer.MIN_VALUE;
	public int Abs(int num) {
		if (num < 0)
			return -num;
		else
			return num;
	}

}
