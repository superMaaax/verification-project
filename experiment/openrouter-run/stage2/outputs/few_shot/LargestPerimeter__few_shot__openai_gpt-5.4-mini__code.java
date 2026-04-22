class LargestPerimeter {
    //@ requires A != null;
    //@ ensures \result == 0 || (\exists int i; 2 <= i && i < A.length; \result == A[i - 2] + A[i - 1] + A[i] && A[i - 2] + A[i - 1] > A[i]);
    //@ ensures \result >= 0;
    public int largestPerimeter(int[] A) {
        Arrays.sort(A);
        //@ maintaining -1 <= i && i < A.length;
        //@ decreases i - 1;
        for (int i = A.length - 1; i >= 2; --i) {
            if (A[i - 2] + A[i - 1] > A[i]) {
                return A[i - 2] + A[i - 1] + A[i];
            }
        }
        return 0;
    }
}
