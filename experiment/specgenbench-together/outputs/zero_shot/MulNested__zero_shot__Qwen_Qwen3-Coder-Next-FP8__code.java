public class MulNested {
    /*@ spec_public @*/
    public static int mulNested(int x, int y) {
        /*@ 
            requires true;
            ensures \result == x * y;
        @*/
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
            loop_invariant 0 <= i && i <= m;
            loop_invariant res == i * n;
            decreases m - i;
        @*/
        for(int i = 0; i < m; i++) {
            /*@ 
                loop_invariant 0 <= j && j <= n;
                loop_invariant res == (i * n) + j;
                decreases n - j;
            @*/
            for(int j = 0; j < n; j++) {
                res = res + 1;
            }
        }
        return sign * res;
    }
}
