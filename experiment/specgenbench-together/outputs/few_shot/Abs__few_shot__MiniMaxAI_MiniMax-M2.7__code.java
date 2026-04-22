public class Abs {
	
	//@ requires num != Integer.MIN_VALUE;
	//@ ensures \result >= 0;
	//@ ensures \result == (num < 0 ? -num : num);
	public int Abs(int num) {
		if (num < 0)
			return -num;
		else
			return num;
	}

}
