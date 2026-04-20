public class TransposeMatrix {
 
       /*@ spec_public @*/
       public int[][] transposeMat(int[][] matrix)
       requires matrix != null;
       requires matrix.length > 0;
       requires matrix[0] != null;
       requires (\forall int i; 0 <= i && i < matrix.length; matrix[i] != null);
       requires (\forall int i, j; 0 <= i && i < matrix.length && 0 <= j && j < matrix[i].length; matrix[i][j] != null);
       ensures \result != null;
       ensures \result.length == matrix[0].length;
       ensures \result[0].length == matrix.length;
       ensures (\forall int i, j; 0 <= i && i < matrix.length && 0 <= j && j < matrix[0].length; \result[j][i] == matrix[i][j]);
       {
          int m, n, p, q;
          m = matrix.length;
          n = matrix[0].length;
          int[][] transpose = new int[n][m];

          /*@ loop_invariant 0 <= c && c <= n;
            @ loop_invariant (\forall int i, j; 0 <= i && i < c && 0 <= j && j < m; transpose[i][j] == matrix[j][i]);
            @ decreases n - c;
            @*/
          for (int c = 0; c < n; c++) {

              /*@ loop_invariant 0 <= d && d <= m;
                @ loop_invariant (\forall int i, j; 0 <= i && i < c && 0 <= j && j < m; transpose[i][j] == matrix[j][i]);
                @ loop_invariant (\forall int j; 0 <= j && j < d; transpose[c][j] == matrix[j][c]);
                @ decreases m - d;
                @*/
              for (int d = 0; d < m; d++) {
                  transpose[c][d] = matrix[d][c];
              }

          }
          return transpose;
       }
}
