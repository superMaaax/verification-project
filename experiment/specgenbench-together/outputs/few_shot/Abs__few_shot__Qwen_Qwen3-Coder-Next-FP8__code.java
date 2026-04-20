public class Abs {
	
	//@ ensures \result == num || \result == -num;
	//@ ensures \result >= 0;
	//@ ensures (\result == num) <==> (num >= 0);
	public int Abs(int num) {
		if (num < 0)
			return -num;
		else
			return num;
	}

}
