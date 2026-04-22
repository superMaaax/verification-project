public class TransposeMatrix {
 
       //@ requires matrix != null && matrix.length > 0 && matrix[0] != null;
       //@ requires (\forall int i; 0 <= i && i < matrix.length; matrix[i] != null && matrix[i].length == matrix[0].length);
       //@ ensures \result != null;
       //@ ensures \result.length == matrix[0].length;
       //@ ensures (\forall int i; 0 <= i && i < matrix[0].length; \result[i].length == matrix.length);
       //@ ensures (\forall int i; 0 <= i && i < matrix.length; (\forall int j; 0 <= j && j < matrix[0].length; \result[j][i] == matrix[i][j]));
       public int[][] transposeMat(int[][] matrix)
       {
          int m, n, p, q;
          m = matrix.length;
          n = matrix[0].length;
          int[][] transpose = new int[n][m];

          //@ maintaining 0 <= c && c <= n;
          //@ maintaining (\forall int i; 0 <= i && i < c; (\forall int d; 0 <= d && d < m; transpose[i][d] == matrix[d][i]));
          //@ decreases n - c;
          for (int c = 0; c < n; c++) {

              //@ maintaining 0 <= d && d <= m;
              //@ maintaining (\forall int i; 0 <= i && i < c; (\forall int j; 0 <= j && j < m; transpose[i][j] == matrix[j][i]));
              //@ maintaining (\forall int j; 0 <= j && j < d; transpose[c][j] == matrix[j][c]);
              //@ decreases m - d;
              for (int d = 0; d < m; d++) {
                  transpose[c][d] = matrix[d][c];
              }

          }
          return transpose;
       }
}
