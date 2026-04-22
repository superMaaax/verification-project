public class Absolute {
	
	/*@
	  @ requires true;
	  @ ensures \result == ((0 <= num) ? num : -num);
	  @*/
	public  int Absolute(int num) {
		if (0 <= num)
			return num;
		else
			return -num;
	}

	/*@
	  @ requires true;
	  @ ensures \result == ((0 <= num) ? num : -num);
	  @*/
	public  long Absolute(long num) {
		if (0 <= num)
			return num;
		else
			return -num;	
	}
}
