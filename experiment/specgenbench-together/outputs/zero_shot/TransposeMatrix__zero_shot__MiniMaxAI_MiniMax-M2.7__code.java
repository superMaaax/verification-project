public class TransposeMatrix {
 
       /*@
        @ requires matrix != null;
        @ requires matrix.length > 0;
        @ requires matrix[0] != null;
        @ requires (\forall int i; 0 <= i < matrix.length; matrix[i] != null && matrix[i].length == matrix[0].length);
        @
        @ ensures \result != null;
        @ ensures \result.length == matrix[0].length;
        @ ensures (\forall int i; 0 <= i < matrix.length; \result[i].length == matrix.length);
        @ ensures (\forall int i; 0 <= i < matrix.length; 
        @                  (\forall int j; 0 <= j < matrix[0].length; \result[j][i] == \old(matrix[i][j])));
        @
        @*/
       public int[][] transposeMat(int[][] matrix)
       {
          int m, n, p, q;
          m = matrix.length;
          n = matrix[0].length;
          int[][] transpose = new int[n][m];

          /*@
           @ maintaining (\forall int i; 0 <= i < c;
           @                  (\forall int j; 0 <= j < m; transpose[j][i] == \old(matrix[i][j])));
           @ decreases n - c;
           @*/
          for (int c = 0; c < n; c++) {

              /*@
               @ maintaining (\forall int j; 0 <= j < d; transpose[c][j] == \old(matrix[j][c]));
               @ decreases m - d;
               @*/
              for (int d = 0; d < m; d++) {
                  transpose[c][d] = matrix[d][c];
              }

          }
          return transpose;
       }
}
