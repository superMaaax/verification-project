public class MatrixAdd {
    
    //@ requires a != null && b != null;
    //@ requires a.length == 2 && b.length == 2;
    //@ requires (\forall int r; 0 <= r && r < 2; a[r] != null && b[r] != null && a[r].length == 2 && b[r].length == 2);
    //@ ensures (\forall int r; 0 <= r && r < 2; (\forall int c; 0 <= c && c < 2; \result[r][c] == a[r][c] + b[r][c]));
    public int[][] add(int[][] a, int[][] b) {
        int[][] c = new int[2][2];
        //@ maintaining 0 <= i && i <= 2;
        //@ decreases 2 - i;
        for (int i = 0; i < 2; i++) {
            //@ maintaining 0 <= j && j <= 2;
            //@ decreases 2 - j;
            for (int j = 0; j < 2; j++) {
                c[i][j] = a[i][j] + b[i][j];
            }
        }
        return c;
    }

}
