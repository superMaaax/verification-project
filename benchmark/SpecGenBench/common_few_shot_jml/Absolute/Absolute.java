public class Absolute {
	
	//@ requires num != Integer.MIN_VALUE;
	//@ ensures num < 0 ==> \result == -num;
	//@ ensures num >= 0 ==> \result == num;
	//@ ensures \result >= 0;
	public  int Absolute(int num) {
		if (0 <= num)
			return num;
		else
			return -num;
	}

	
	//@ requires num != Long.MIN_VALUE;
	//@ ensures num < 0 ==> \result == -num;
	//@ ensures num >= 0 ==> \result == num;
	//@ ensures \result >= 0;
	public  long Absolute(long num) {
		if (0 <= num)
			return num;
		else
			return -num;	
	}
}
