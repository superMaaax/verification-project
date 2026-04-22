public class MulNested {
    /*@
      @ requires true;
      @ ensures \result == x * y;
      @ assignable \nothing;
      @*/
    public static int mulNested(int x, int y) {
        int res = 0, m = x, n = y, sign = 1;
        if(x < 0){
            m = -x;
            sign = -sign;
        }
        if(y < 0){
            n = -y;
            sign = -sign;
        }
        /*@
          @ loop invariant 0 <= i && i <= m;
          @ loop invariant res == i * n;
          @ decreases m - i;
          @*/
        for(int i = 0; i < m; i++) {
            /*@
              @ loop invariant 0 <= j && j <= n;
              @ loop invariant res == i * n + j;
              @ decreases n - j;
              @*/
            for(int j = 0; j < n; j++) {
                res = res + 1;
            }
        }
        return sign * res;
    }
}
