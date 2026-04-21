public class MatrixMul {
    
    //@ requires a != null && b != null;
    //@ requires a.length == 2 && b.length == 2;
    //@ requires (\forall int r; 0 <= r && r < 2; a[r] != null && a[r].length == 2 && b[r] != null && b[r].length == 2);
    //@ ensures \result != null && \result.length == 2;
    //@ ensures (\forall int r; 0 <= r && r < 2; \result[r] != null && \result[r].length == 2);
    //@ ensures (\forall int i, j; 0 <= i && i < 2 && 0 <= j && j < 2; \result[i][j] == a[i][0] * b[0][j] + a[i][1] * b[1][j]);
    public int[][] multiply(int[][] a, int[][] b) {
        int[][] c = new int[2][2];
        //@ maintaining 0 <= i && i <= 2;
        //@ maintaining (\forall int ii, jj; 0 <= ii && ii < i && 0 <= jj && jj < 2; c[ii][jj] == a[ii][0] * b[0][jj] + a[ii][1] * b[1][jj]);
        //@ decreases 2 - i;
        for (int i = 0; i < 2; i++) {
            //@ maintaining 0 <= j && j <= 2;
            //@ maintaining (\forall int jj; 0 <= jj && jj < j; c[i][jj] == a[i][0] * b[0][jj] + a[i][1] * b[1][jj]);
            //@ decreases 2 - j;
            for (int j = 0; j < 2; j++) {
                c[i][j] = a[i][0] * b[0][j] + a[i][1] * b[1][j];
            }
        }
        return c;
    }

}
