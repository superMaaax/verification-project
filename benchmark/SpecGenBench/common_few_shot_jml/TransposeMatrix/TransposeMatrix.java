public class TransposeMatrix {
 
       //@ requires matrix != null && matrix.length > 0 && matrix[0] != null;
       //@ ensures \result != null && \result.length == matrix[0].length && (\forall int r; 0 <= r && r < \result.length; \result[r] != null && \result[r].length == matrix.length);
       //@ ensures (\forall int c; 0 <= c && c < matrix[0].length; (\forall int d; 0 <= d && d < matrix.length; \result[c][d] == matrix[d][c]));
       public int[][] transposeMat(int[][] matrix)
       {
          int m, n, p, q;
          m = matrix.length;
          n = matrix[0].length;
          int[][] transpose = new int[n][m];

          //@ maintaining 0 <= c && c <= n;
          //@ decreases n - c;
          for (int c = 0; c < n; c++) {

              //@ maintaining 0 <= d && d <= m;
              //@ decreases m - d;
              for (int d = 0; d < m; d++) {
                  transpose[c][d] = matrix[d][c];
              }

          }
          return transpose;
       }
}


