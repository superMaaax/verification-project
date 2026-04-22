public class MatrixAdd {
    
    /*@ requires a != null && b != null;
        requires a.length == 2 && b.length == 2;
        requires (\forall int i; 0 <= i && i < 2; a[i] != null && b[i] != null && a[i].length == 2 && b[i].length == 2);
        ensures \result != null;
        ensures \result.length == 2;
        ensures (\forall int i; 0 <= i && i < 2; \result[i] != null && \result[i].length == 2);
        ensures (\forall int i; 0 <= i && i < 2; (\forall int j; 0 <= j && j < 2; \result[i][j] == a[i][j] + b[i][j]));
    @*/
    public int[][] add(int[][] a, int[][] b) {
        int[][] c = new int[2][2];
        /*@ maintaining 0 <= i && i <= 2;
            decreases 2 - i;
        @*/
        for (int i = 0; i < 2; i++) {
            /*@ maintaining 0 <= j && j <= 2;
                decreases 2 - j;
            @*/
            for (int j = 0; j < 2; j++) {
                c[i][j] = a[i][j] + b[i][j];
            }
        }
        return c;
    }
}
