class NumberOfCutsSeq {
    /*@
      @ requires n >= 1;
      @ ensures \result == ((n == 1) ? 0 : ((n % 2 == 0) ? (n / 2) : n));
      @*/
    public int numberOfCuts(int n) {
        return ((n == 1) ? 0 : ((n % 2 == 0) ? (n / 2) : n));
    }
}
