class PrimeCheck {
   
	//@ requires a >= 2;
	//@ ensures \result ==> (\forall int k; 2 <= k && k <= a / 2; a % k != 0);
	//@ ensures !\result ==> (\exists int k; 2 <= k && k <= a / 2; a % k == 0);
	public boolean isPrime(int a) {
		int i = 2;
		int mid = a/2;

		//@ maintaining 2 <= i && i <= mid + 1;
		//@ maintaining (\forall int k; 2 <= k && k < i; a % k != 0);
		//@ decreases mid + 1 - i;
		while (i <= mid) {
			if (a%i == 0)
				return false;
			i++;
		}
		return true;
	}
}
