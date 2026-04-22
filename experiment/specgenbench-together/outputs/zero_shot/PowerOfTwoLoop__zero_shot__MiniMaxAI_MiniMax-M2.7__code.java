class PowerOfTwoLoop {
    /*@ 
      @ public normal_behavior
      @ ensures \result == true <==> (n > 0 && (n & (n - 1)) == 0);
      @ also
      @ public exceptional_behavior
      @ requires n == Integer.MIN_VALUE;
      @*/
    public boolean isPowerOfTwo(int n) {
        if(n <= 0) {
            return false;
        }
        //@ loop_invariant n > 0 && (n & (n - 1)) == 0;
        //@ decreases n;
        while (n % 2 == 0) {
            n /= 2;
        }
        return n == 1;
    }
}
