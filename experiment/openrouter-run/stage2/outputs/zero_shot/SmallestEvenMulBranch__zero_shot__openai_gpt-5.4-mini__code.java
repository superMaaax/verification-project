class SmallestEvenMulBranch {
    /*@ public normal_behavior
      @ requires true;
      @ ensures (\result % 2 == 0) && (\result > 0);
      @ ensures (\result % n == 0);
      @ ensures (\result == n) <==> (n % 2 == 0);
      @*/
    public int smallestEvenMultiple(int n) {
        if(n % 2 == 0) {
            return n;
        }
        return 2 * n;
    }
}
