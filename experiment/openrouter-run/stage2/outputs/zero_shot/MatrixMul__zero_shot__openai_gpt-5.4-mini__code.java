public class MatrixMul {
    
    /*@ public normal_behavior
      @ requires a != null && b != null;
      @ requires a.length >= 2 && b.length >= 2;
      @ requires a[0] != null && a[1] != null && b[0] != null && b[1] != null;
      @ requires a[0].length >= 2 && a[1].length >= 2;
      @ requires b[0].length >= 2 && b[1].length >= 2;
      @ ensures \result != null;
      @ ensures \result.length == 2;
      @ ensures \result[0] != null && \result[1] != null;
      @ ensures \result[0].length == 2;
      @ ensures \result[1].length == 2;
      @ ensures \result[0][0] == a[0][0] * b[0][0] + a[0][1] * b[1][0];
      @ ensures \result[0][1] == a[0][0] * b[0][1] + a[0][1] * b[1][1];
      @ ensures \result[1][0] == a[1][0] * b[0][0] + a[1][1] * b[1][0];
      @ ensures \result[1][1] == a[1][0] * b[0][1] + a[1][1] * b[1][1];
      @ also
      @ public exceptional_behavior
      @ requires a == null || b == null;
      @ signals (NullPointerException e) true;
      @*/
    public int[][] multiply(int[][] a, int[][] b) {
        int[][] c = new int[2][2];
        /*@
          @ loop invariant 0 <= i && i <= 2;
          @ loop invariant c != null && c.length == 2;
          @ loop invariant c[0] != null && c[1] != null;
          @ loop invariant (\forall int x; 0 <= x && x < i ==>
          @     c[x][0] == a[x][0] * b[0][0] + a[x][1] * b[1][0] &&
          @     c[x][1] == a[x][0] * b[0][1] + a[x][1] * b[1][1]);
          @ decreases 2 - i;
          @*/
        for (int i = 0; i < 2; i++) {
            /*@
              @ loop invariant 0 <= j && j <= 2;
              @ loop invariant c != null && c.length == 2;
              @ loop invariant c[i] != null;
              @ loop invariant (\forall int y; 0 <= y && y < j ==>
              @     c[i][y] == a[i][0] * b[0][y] + a[i][1] * b[1][y]);
              @ decreases 2 - j;
              @*/
            for (int j = 0; j < 2; j++) {
                c[i][j] = a[i][0] * b[0][j] + a[i][1] * b[1][j];
            }
        }
        return c;
    }

}
