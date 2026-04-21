public class AbsSeq {
	
	//@ requires num != Integer.MIN_VALUE;
	//@ ensures num < 0 ==> \result == -num;
	//@ ensures num >= 0 ==> \result == num;
	//@ ensures \result >= 0;
	public int Abs(int num) {
		return ((num < 0) ? (-num) : (num));
	}

}
