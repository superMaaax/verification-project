class SmallestEvenMul {
    /*@ 
      @ requires true;
      @ ensures \result % 2 == 0;
      @ ensures (\result % n == 0) || n == 0;
      @ ensures (n % 2 == 0) ==> (\result == n);
      @ ensures (n % 2 != 0) ==> (\result == 2 * n);
      @*/
    public int smallestEvenMultiple(int n) {
        return n % 2 == 0 ? n : 2 * n;
    }
}
