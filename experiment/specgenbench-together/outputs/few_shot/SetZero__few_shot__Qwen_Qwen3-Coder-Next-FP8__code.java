public class SetZero {
    //@ requires 0 <= iBegin && iBegin <= iEnd && iEnd <= a.length;
    //@ ensures (\forall int k; iBegin <= k && k < iEnd; a[k] == 0);
    public static void SetZero(int[] a, int iBegin, int iEnd) {
        int k = iBegin;
        //@ maintaining iBegin <= k && k <= iEnd;
        //@ maintaining (\forall int j; iBegin <= j && j < k; a[j] == 0);
        //@ decreases iEnd - k;
        while (k < iEnd) {
            a[k] = 0;
            k = k + 1;
        }
    }
}
