public class NegAbs {
	
	/*@ spec_public @*/
	public int negAbs(int num) {
		requires true;
		ensures \result == (num < 0 ? num : -num);
		ensures \result <= 0;
		ensures (\result == 0) <==> (num == 0);
		ensures (\result < 0) <==> (num != 0);
		
		if (num < 0)
			return num;
		else
			return -num;
	}

}
