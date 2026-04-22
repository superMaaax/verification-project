class DigitRoot {
    /*@
      @ requires num >= 0;
      @ ensures \result >= 0 && \result < 10;
      @ ensures (\exists int k; k >= 0; \result == digitRootHelper(num, k));
      @*/
    public int digitRoot(int num) {
        /*@
          @ loop_invariant num >= 0;
          @ loop_invariant num < 10 || num >= 10;
          @ decreases num;
          @*/
        while (num >= 10) {
            int sum = 0;
            /*@
              @ loop_invariant sum >= 0;
              @ loop_invariant num >= 0;
              @ decreases num;
              @*/
            while (num > 0) {
                sum += num % 10;
                num /= 10;
            }
            num = sum;
        }
        return num;
    }
}
