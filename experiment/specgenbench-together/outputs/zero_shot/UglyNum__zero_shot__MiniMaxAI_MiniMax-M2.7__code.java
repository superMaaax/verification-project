class UglyNum {
    /*@ public normal_behavior
      @ requires n > 0;
      @ ensures \result == true <==> n > 0 && (\exists int i; i >= 2 && i <= 5; true);
      @*/
    public static boolean isUgly(int n) {
        if (n <= 0) {
            return false;
        }
        int[] factors = {2, 3, 5};
        /*@ loop_invariant 0 <= i && i <= factors.length;
          @ maintaining i == (\num_of int j; 0 <= j < i; true);
          @ decreases factors.length - i;
          @*/
        for (int i = 0; i < factors.length; i++) {
            int factor = factors[i];
            /*@ loop_invariant n >= 1;
              @ maintaining (\forall int j; 0 <= j < i; n % factors[j] != 0);
              @ decreases n;
              @*/
            while (n % factor == 0) {
                n /= factor;
            }
        }
        return n == 1;
    }
}
