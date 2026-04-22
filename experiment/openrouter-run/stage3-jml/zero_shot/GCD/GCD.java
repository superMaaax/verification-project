public class GCD {
	
	/*@
	  @ requires true;
	  @ ensures (num1 == 0 && num2 == 0) ==> \result == -1;
	  @ ensures !(num1 == 0 && num2 == 0) && ((num1 == 0) || (num2 == 0)) ==> \result == ((num1 < 0 ? -num1 : num1) > (num2 < 0 ? -num2 : num2) ? (num1 < 0 ? -num1 : num1) : (num2 < 0 ? -num2 : num2));
	  @ ensures !(num1 == 0 && num2 == 0) && !(num1 == 0 || num2 == 0) ==> \result >= 1;
	  @*/
	public static int gcd(int num1, int num2){
		int result = 1; 
	 	num1 = (0 <= num1) ? num1 : -num1;
		num2 = (0 <= num2) ? num2 : -num2;

		if (num1 == 0 && num2 == 0) {
			return -1;	
		}

		if (num1 == 0 || num2 == 0) { 
			return (num1 > num2) ? num1 : num2;
		}

		/*@
		  @ loop_invariant 1 <= i && i <= num1 + 1;
		  @ loop_invariant 1 <= i && i <= num2 + 1;
		  @ loop_invariant result >= 1;
		  @ loop_invariant (\forall int k; 1 <= k && k < i && k <= num1 && k <= num2; (num1 % k == 0 && num2 % k == 0) ==> k <= result);
		  @ decreases (num1 < num2 ? num1 : num2) - i + 1;
		  @*/
		for (int i = 1; i <= num1 && i <= num2; i++) {
            if (num1 % i == 0 && num2 % i == 0) {
       			result = i;
			}
        }
		return result;
	}
}
