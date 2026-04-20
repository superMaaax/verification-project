class NumberOfCutsSeq {
    //@ ensures \result == 0 <==> n == 1;
    //@ ensures \result == n / 2 <==> n != 1 && n % 2 == 0;
    //@ ensures \result == n <==> n != 1 && n % 2 != 0;
    public int numberOfCuts(int n) {
        return ((n == 1) ? 0 : ((n % 2 == 0) ? (n / 2) : n));
    }
}
