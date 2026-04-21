public class CopyArray {
 
    //@ requires a != null && b != null;
    //@ requires 0 <= iBegin && iBegin <= iEnd && iEnd <= a.length && iEnd <= b.length;
    //@ ensures (\forall int k; iBegin <= k && k < iEnd; a[k] == b[k]);
    public static void CopyArray(int[] b, int iBegin, int iEnd, int[] a) {
        int k = iBegin;

        //@ maintaining iBegin <= k && k <= iEnd;
        //@ maintaining (\forall int t; iBegin <= t && t < k; a[t] == b[t]);
        //@ decreases iEnd - k;
        while (iEnd - k > 0) {
            a[k] = b[k];
            k = k + 1 ;
        }
    }
}

