class UglyNumLoop {
    /*@ public normal_behavior
      @ requires true;
      @ ensures \result <==> (n > 0 && (\exists int a, b, c;
      @                         a >= 0 && b >= 0 && c >= 0 &&
      @                         n == (1 * (\bigint)2 \pow a) * (\bigint)3 \pow b * (\bigint)5 \pow c));
      @*/
    public static boolean isUgly(int n) {
        if (n <= 0) {
            return false;
        }
        /*@ loop_invariant n > 0;
          @ loop_invariant (\forall int k; k >= 0; k < 31; true);
          @ decreases n;
          @*/
        while (n % 2 == 0) {
            n /= 2;
        }
        /*@ loop_invariant n > 0;
          @ decreases n;
          @*/
        while (n % 3 == 0) {
            n /= 3;
        }
        /*@ loop_invariant n > 0;
          @ decreases n;
          @*/
        while (n % 5 == 0) {
            n /= 5;
        }
        return n == 1;
    }
}
