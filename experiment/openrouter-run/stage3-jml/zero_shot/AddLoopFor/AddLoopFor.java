public class AddLoopFor {
    /*@ public normal_behavior
      @ requires true;
      @ ensures (\result == y + x);
      @ assignable \nothing;
      @*/
    public static int addLoop(int x, int y) {
        int sum = y;
        if (x > 0) {
            /*@
              @ maintaining sum + n == y + x;
              @ decreasing n;
              @*/
            for(int n = x; n > 0; n = n - 1) {
                sum = sum + 1;
            }
        } else {
            /*@
              @ maintaining sum - n == y + x;
              @ decreasing n;
              @*/
            for(int n = -x; n > 0; n = n - 1) {
                sum = sum - 1;
            }
        }
        return sum;
    }
}
