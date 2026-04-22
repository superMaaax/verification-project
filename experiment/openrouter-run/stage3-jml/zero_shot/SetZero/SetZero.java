public class SetZero {

	public static /*@ requires a != null;
	  @ requires 0 <= iBegin && iBegin <= iEnd && iEnd <= a.length;
	  @ ensures (\forall int j; 0 <= j && j < a.length;
	  @            (iBegin <= j && j < iEnd) ==> a[j] == 0);
	  @ ensures (\forall int j; 0 <= j && j < a.length;
	  @            (j < iBegin || j >= iEnd) ==> a[j] == \old(a[j]));
	  @*/
	void SetZero(int[] a, int iBegin, int iEnd) {
		int k = iBegin;

		/*@ loop_invariant iBegin <= k && k <= iEnd;
		  @ loop_invariant (\forall int j; iBegin <= j && j < k; a[j] == 0);
		  @ loop_invariant (\forall int j; 0 <= j && j < a.length;
		  @                   (j < iBegin || j >= iEnd) ==> a[j] == \old(a[j]));
		  @ decreases iEnd - k;
		  @*/
		while (k < iEnd) {
            		a[k] = 0;
            		k = k + 1 ;
        	}
	}
}
