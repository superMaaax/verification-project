public class Abs {
	
	/*@ spec_public @*/
	public int Abs(int num) {
		requires true;
		ensures \result >= 0;
		ensures (\result == num && num >= 0) || (\result == -num && num < 0);
		
		if (num < 0)
			return -num;
		else
			return num;
	}

}
