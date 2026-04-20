public class MatrixMul {
    
    /*@ spec_public @*/
    public int[][] multiply(int[][] a, int[][] b) {
        requires a != null && b != null;
        requires a.length == 2 && b.length == 2;
        requires (\forall int i; 0 <= i && i < 2; a[i].length == 2);
        requires (\forall int i; 0 <= i && i < 2; b[i].length == 2);
        
        ensures result != null;
        ensures result.length == 2;
        ensures (\forall int i; 0 <= i && i < 2; result[i].length == 2);
        ensures (\forall int i, j; 0 <= i && i < 2 && 0 <= j && j < 2;
            result[i][j] == a[i][0] * b[0][j] + a[i][1] * b[1][j]);
        
        int[][] c = new int[2][2];
        /*@ loop_invariant 0 <= i && i <= 2;
            loop_invariant (\forall int k, l; 0 <= k && k < i && 0 <= l && l < 2;
                c[k][l] == a[k][0] * b[0][l] + a[k][1] * b[1][l]);
            decreases 2 - i;
        @*/
        for (int i = 0; i < 2; i++) {
            /*@ loop_invariant 0 <= j && j <= 2;
                loop_invariant (\forall int k, l; 0 <= k && k <= i && 0 <= l && l < 2;
                    c[k][l] == a[k][0] * b[0][l] + a[k][1] * b[1][l]);
                decreases 2 - j;
            @*/
            for (int j = 0; j < 2; j++) {
                c[i][j] = a[i][0] * b[0][j] + a[i][1] * b[1][j];
            }
        }
        return c;
    }

}
