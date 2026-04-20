public class NegAbs {
	
	/*@ requires true;
	  @ ensures \result == -Math.abs(num);
	  @*/
	public int negAbs(int num) {
		if (num < 0)
			return num;
		else
			return -num;
	}

}
