import java.util.Arrays;

class LargestPerimeter {
    //@ requires A != null;
    //@ ensures \result >= 0;
    public int largestPerimeter(int[] A) {
        Arrays.sort(A);
        //@ maintaining -1 <= i && i <= A.length - 1;
        //@ maintaining (\forall int t; i < t && t <= A.length - 1; !(A[t - 2] + A[t - 1] > A[t]));
        //@ decreases i + 1;
        for (int i = A.length - 1; i >= 2; --i) {
            if (A[i - 2] + A[i - 1] > A[i]) {
                return A[i - 2] + A[i - 1] + A[i];
            }
        }
        return 0;
    }
}
