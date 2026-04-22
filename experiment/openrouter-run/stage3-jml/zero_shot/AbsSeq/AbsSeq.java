public class AbsSeq {
	
	/*@ public normal_behavior
	  @ requires true;
	  @ ensures \result >= 0;
	  @ ensures \result == ((num < 0) ? (-num) : num);
	  @ assignable \nothing;
	  @*/
	public int Abs(int num) {
		return ((num < 0) ? (-num) : (num));
	}

}
