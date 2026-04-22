public class Abs {
	
	/*@ public normal_behavior
	  @ requires true;
	  @ ensures \result == (num < 0 ? -num : num);
	  @ assignable \nothing;
	  @*/
	public int Abs(int num) {
		if (num < 0)
			return -num;
		else
			return num;
	}

}
