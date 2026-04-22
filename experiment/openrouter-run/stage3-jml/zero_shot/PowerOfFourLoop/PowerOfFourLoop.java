class PowerOfFourLoop {
    /*@ public normal_behavior
      @ requires true;
      @ ensures \result <==> (\exists int k; k >= 0; n == (int) Math.pow(4, k));
      @ assignable \nothing;
      @*/
    public boolean isPowerOfFour(int n) {
        if(n <= 0) {
            return false;
        }
        /*@ maintaining n > 0;
          @ maintaining (\exists int k; k >= 0; \old(n) == n * (int) Math.pow(4, k));
          @ decreases n;
          @*/
        while (n % 4 == 0) {
            n /= 4;
        }
        return n == 1;
    }
}
