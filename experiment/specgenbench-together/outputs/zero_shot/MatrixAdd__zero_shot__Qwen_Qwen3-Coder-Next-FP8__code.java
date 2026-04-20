public class MatrixAdd {
    
    /*@ spec_public @*/
    public int[][] add(int[][] a, int[][] b) {
        requires a != null;
        requires b != null;
        requires a.length == 2;
        requires b.length == 2;
        requires (\forall int i; 0 <= i && i < 2; a[i].length == 2);
        requires (\forall int i; 0 <= i && i < 2; b[i].length == 2);
        
        ensures \result != null;
        ensures \result.length == 2;
        ensures (\forall int i; 0 <= i && i < 2; \result[i].length == 2);
        ensures (\forall int i; 0 <= i && i < 2; 
            (\forall int j; 0 <= j && j < 2; 
                \result[i][j] == a[i][j] + b[i][j]));
        
        int[][] c = new int[2][2];
        /*@ loop_invariant 0 <= i && i <= 2;
            loop_invariant (\forall int k; 0 <= k && k < i; 
                (\forall int l; 0 <= l && l < 2; c[k][l] == a[k][l] + b[k][l]));
            decreases 2 - i;
        @*/
        for (int i = 0; i < 2; i++) {
            /*@ loop_invariant 0 <= j && j <= 2;
                loop_invariant (\forall int k; 0 <= k && k <= i; 
                    (\forall int l; 0 <= l && l < (k == i ? j : 2); 
                        c[k][l] == a[k][l] + b[k][l]));
                decreases 2 - j;
            @*/
            for (int j = 0; j < 2; j++) {
                c[i][j] = a[i][j] + b[i][j];
            }
        }
        return c;
    }

}
