public class Abs {
	
	/*@ 
	  @ public normal_behavior
	  @ requires true;
	  @ ensures \result >= 0;
	  @ ensures (num >= 0 ==> \result == num) && (num < 0 ==> \result == -num);
	  @*/
	public int Abs(int num) {
		if (num < 0)
			return -num;
		else
			return num;
	}

}
