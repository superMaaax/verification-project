public class AddTwoLoop {
    public static int add(int a, int b) {
        int m = ((a < 0) ? -a : a);
        int n = ((b < 0) ? -b : b);
        int res = 0;
        //@ loop_modifies res;
        //@ loop_invariant 0 <= i && i <= m;
        //@ loop_invariant res == i * ((a < 0) ? -1 : 1);
        //@ decreases m - i;
        for(int i = 0; i < m; i++){
            res = res + ((a < 0) ? -1 : 1);
        }
        //@ loop_modifies res;
        //@ loop_invariant m <= i && i <= m + n;
        //@ loop_invariant res == m * ((a < 0) ? -1 : 1) + (i - m) * ((b < 0) ? -1 : 1);
        //@ decreases m + n - i;
        for(int i = 0; i < n; i++){
            res = res + ((b < 0) ? -1 : 1);
        }
        return res;
    }
}
