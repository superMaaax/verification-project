public class SubLoop {
    /*@ public normal_behavior
      @ requires true;
      @ ensures \result == x - y;
      @ assignable \nothing;
      @*/
    public static int subLoop(int x, int y) {
        int sum = x;
        if (y > 0) {
            int n = y;
            /*@ loop_invariant sum + n == x + y;
              @ loop_invariant n >= 0;
              @ decreases n;
              @*/
            while (n > 0) {
                sum = sum - 1;
                n = n - 1;
            }
        } else {
            int n = -y;
            /*@ loop_invariant sum - n == x + y;
              @ loop_invariant n >= 0;
              @ decreases n;
              @*/
            while (n > 0) {
                sum = sum + 1;
                n = n - 1;
            }
        }
        return sum;
    }
}
