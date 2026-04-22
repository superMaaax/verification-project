public class TransposeMatrix {
 
       /*@
         @ requires matrix != null;
         @ requires matrix.length > 0;
         @ requires (\forall int i; 0 <= i && i < matrix.length; matrix[i] != null);
         @ requires (\forall int i; 0 <= i && i < matrix.length; matrix[i].length == matrix[0].length);
         @ ensures \result != null;
         @ ensures \result.length == matrix[0].length;
         @ ensures (\forall int i; 0 <= i && i < \result.length; \result[i].length == matrix.length);
         @ ensures (\forall int i, j; 0 <= i && i < matrix.length && 0 <= j && j < matrix[0].length;
         @             \result[j][i] == matrix[i][j]);
         @*/
       public int[][] transposeMat(int[][] matrix)
       {
          int m, n, p, q;
          m = matrix.length;
          n = matrix[0].length;
          int[][] transpose = new int[n][m];

          /*@
            @ loop invariant 0 <= c && c <= n;
            @ loop invariant (\forall int j; 0 <= j && j < c;
            @                     (\forall int i; 0 <= i && i < m; transpose[j][i] == matrix[i][j]));
            @ decreases n - c;
            @*/
          for (int c = 0; c < n; c++) {

              /*@
                @ loop invariant 0 <= d && d <= m;
                @ loop invariant (\forall int i; 0 <= i && i < d; transpose[c][i] == matrix[i][c]);
                @ decreases m - d;
                @*/
              for (int d = 0; d < m; d++) {
                  transpose[c][d] = matrix[d][c];
              }

          }
          return transpose;
       }
}
