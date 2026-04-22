class SmallestEvenMul {
    //@ requires n != Integer.MIN_VALUE;
    //@ ensures (\result % 2 == 0) && (\result == n || \result == 2 * n);
    //@ ensures n % 2 == 0 ==> \result == n;
    //@ ensures n % 2 != 0 ==> \result == 2 * n;
    public int smallestEvenMultiple(int n) {
        return n % 2 == 0 ? n : 2 * n;
    }
}
