public class AddTwoLoop {
    /*@ public normal_behavior
      @ requires true;
      @ ensures \result == a + b;
      @ assignable \nothing;
      @*/
    public static int add(int a, int b) {
        int m = ((a < 0) ? -a : a);
        int n = ((b < 0) ? -b : b);
        int res = 0;
        /*@
          @ loop invariant 0 <= i && i <= m;
          @ loop invariant res == (((a < 0) ? -1 : 1) * i);
          @ decreases m - i;
          @*/
        for(int i = 0; i < m; i++){
            res = res + ((a < 0) ? -1 : 1);
        }
        /*@
          @ loop invariant 0 <= i && i <= n;
          @ loop invariant res == a + (((b < 0) ? -1 : 1) * i);
          @ decreases n - i;
          @*/
        for(int i = 0; i < n; i++){
            res = res + ((b < 0) ? -1 : 1);
        }
        return res;
    }
}
