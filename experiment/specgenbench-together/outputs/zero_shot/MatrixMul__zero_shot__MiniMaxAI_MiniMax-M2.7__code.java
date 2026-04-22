public class MatrixMul {
    
    /*@ requires a != null && a.length == 2 && a[0] != null && a[0].length == 2 
      @         && a[1] != null && a[1].length == 2;
      @ requires b != null && b.length == 2 && b[0] != null && b[0].length == 2 
      @         && b[1] != null && b[1].length == 2;
      @ ensures \result != null && \result.length == 2 && \result[0] != null 
      @        && \result[0].length == 2 && \result[1] != null && \result[1].length == 2;
      @ ensures \result[0][0] == a[0][0] * b[0][0] + a[0][1] * b[1][0];
      @ ensures \result[0][1] == a[0][0] * b[0][1] + a[0][1] * b[1][1];
      @ ensures \result[1][0] == a[1][0] * b[0][0] + a[1][1] * b[1][0];
      @ ensures \result[1][1] == a[1][0] * b[0][1] + a[1][1] * b[1][1];
      @*/
    public int[][] multiply(int[][] a, int[][] b) {
        int[][] c = new int[2][2];
        for (int i = 0; i < 2; i++) {
            /*@ maintaining 0 <= i && i <= 2;
              @ decreases 2 - i;
              @*/
            for (int j = 0; j < 2; j++) {
                /*@ maintaining 0 <= j && j <= 2;
                  @ decreases 2 - j;
                  @*/
                c[i][j] = a[i][0] * b[0][j] + a[i][1] * b[1][j];
            }
        }
        return c;
    }

}
