class PrimeCheck {
   
	/*@ public normal_behavior
	  @ requires true;
	  @ ensures \result <==> (a >= 2 && (\forall int d; 2 <= d && d <= a/2; a % d != 0));
	  @*/
	public boolean isPrime(int a) {
		int i = 2;
		int mid = a/2;

		/*@ maintaining 2 <= i && i <= mid + 1;
		  @ maintaining (\forall int d; 2 <= d && d < i; a % d != 0);
		  @ decreasing mid - i + 1;
		  @*/
		while (i <= mid) {
			if (a%i == 0)
				return false;
			i++;
		}
		return true;
	}
}
