class PowerOfTwo {
    /*@ public normal_behavior
      @ requires true;
      @ ensures \result <==> (n > 0 && (n & (n - 1)) == 0);
      @*/
    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }
}
