public class MatrixMul {
    
    /*@ requires a != null && b != null;
      @ requires a.length >= 2 && b.length >= 2;
      @ requires a[0] != null && a[1] != null && b[0] != null && b[1] != null;
      @ requires a[0].length >= 2 && a[1].length >= 2 && b[0].length >= 2 && b[1].length >= 2;
      @ requires (\forall int i; 0 <= i && i < 2; (\forall int j; 0 <= j && j < 2;
      @             Integer.MIN_VALUE <= a[i][j] * b[0][0] + a[i][j] * b[1][1] &&
      @             a[i][j] * b[0][0] + a[i][j] * b[1][1] <= Integer.MAX_VALUE));
      @ ensures \result != null;
      @ ensures \result.length == 2;
      @ ensures \result[0] != null && \result[1] != null;
      @ ensures \result[0].length == 2 && \result[1].length == 2;
      @ ensures \result[0][0] == a[0][0] * b[0][0] + a[0][1] * b[1][0];
      @ ensures \result[0][1] == a[0][0] * b[0][1] + a[0][1] * b[1][1];
      @ ensures \result[1][0] == a[1][0] * b[0][0] + a[1][1] * b[1][0];
      @ ensures \result[1][1] == a[1][0] * b[0][1] + a[1][1] * b[1][1];
      @*/
    public int[][] multiply(int[][] a, int[][] b) {
        int[][] c = new int[2][2];
        /*@ maintaining 0 <= i && i <= 2;
          @ maintaining (\forall int r; 0 <= r && r < i;
          @                 (\forall int j; 0 <= j && j < 2;
          @                     c[r][j] == a[r][0] * b[0][j] + a[r][1] * b[1][j]));
          @ decreases 2 - i;
          @*/
        for (int i = 0; i < 2; i++) {
            /*@ maintaining 0 <= j && j <= 2;
              @ maintaining (\forall int k; 0 <= k && k < j;
              @                 c[i][k] == a[i][0] * b[0][k] + a[i][1] * b[1][k]);
              @ decreases 2 - j;
              @*/
            for (int j = 0; j < 2; j++) {
                c[i][j] = a[i][0] * b[0][j] + a[i][1] * b[1][j];
            }
        }
        return c;
    }

}
