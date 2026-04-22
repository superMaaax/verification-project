import java.util.Arrays;

class LargestPerimeter {
    /*@ requires A != null;
        ensures \result >= 0;
        ensures \result == 0 ==> (\forall int i; i >= 2 && i < A.length; A[i-2] + A[i-1] <= A[i]);
        ensures \result != 0 ==> (\exists int i; i >= 2 && i < A.length; \result == A[i-2] + A[i-1] + A[i] && A[i-2] + A[i-1] > A[i] && (\forall int j; j >= 2 && j < A.length; (A[j-2] + A[j-1] > A[j]) ==> (A[j-2] + A[j-1] + A[j] <= \result)));
     */
    public int largestPerimeter(int[] A) {
        Arrays.sort(A);
        //@ loop_invariant i >= 2 && i <= A.length - 1;
        //@ maintaining (\forall int k; k > i && k < A.length; A[k-2] + A[k-1] <= A[k]);
        //@ decreases i;
        for (int i = A.length - 1; i >= 2; --i) {
            if (A[i - 2] + A[i - 1] > A[i]) {
                return A[i - 2] + A[i - 1] + A[i];
            }
        }
        return 0;
    }
}
