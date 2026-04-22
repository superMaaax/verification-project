public class MatrixAdd {
    
    /*@ public normal_behavior
      @ requires a != null && b != null;
      @ requires a.length >= 2 && b.length >= 2;
      @ requires a[0] != null && a[1] != null && b[0] != null && b[1] != null;
      @ requires a[0].length >= 2 && a[1].length >= 2;
      @ requires b[0].length >= 2 && b[1].length >= 2;
      @ ensures \result != null;
      @ ensures \result.length == 2;
      @ ensures \result[0].length == 2 && \result[1].length == 2;
      @ ensures (\forall int i; 0 <= i && i < 2;
      @            (\forall int j; 0 <= j && j < 2;
      @                \result[i][j] == a[i][j] + b[i][j]));
      @ assignable \nothing;
      @*/
    public int[][] add(int[][] a, int[][] b) {
        int[][] c = new int[2][2];
        /*@ loop invariant 0 <= i && i <= 2;
          @ loop invariant (\forall int x; 0 <= x && x < i;
          @                    (\forall int y; 0 <= y && y < 2;
          @                        c[x][y] == a[x][y] + b[x][y]));
          @ decreases 2 - i;
          @*/
        for (int i = 0; i < 2; i++) {
            /*@ loop invariant 0 <= j && j <= 2;
              @ loop invariant (\forall int y; 0 <= y && y < j;
              @                    c[i][y] == a[i][y] + b[i][y]);
              @ decreases 2 - j;
              @*/
            for (int j = 0; j < 2; j++) {
                c[i][j] = a[i][j] + b[i][j];
            }
        }
        return c;
    }

}
