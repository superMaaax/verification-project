class NumberOfCutsSeq {
    //@ requires n >= 1;
    //@ ensures \result == (n == 1 ? 0 : (n % 2 == 0 ? n / 2 : n));
    //@ ensures \result >= 0;
    public int numberOfCuts(int n) {
        return ((n == 1) ? 0 : ((n % 2 == 0) ? (n / 2) : n));
    }
}
