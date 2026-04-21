public class Abs {
	
	//@ requires num != Integer.MIN_VALUE;
	//@ ensures num < 0 ==> \result == -num;
	//@ ensures num >= 0 ==> \result == num;
	//@ ensures \result >= 0;
	public int Abs(int num) {
		if (num < 0)
			return -num;
		else
			return num;
	}

}
