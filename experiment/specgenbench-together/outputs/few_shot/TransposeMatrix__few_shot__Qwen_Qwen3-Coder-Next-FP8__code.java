public class TransposeMatrix {
 
    /*@ spec_public @*/
    public int[][] transposeMat(int[][] matrix)
    /*@ requires matrix != null;
        @ requires matrix.length > 0;
        @ requires (\forall int i; 0 <= i && i < matrix.length; matrix[i] != null && matrix[i].length == matrix[0].length);
        @ ensures \result != null;
        @ ensures \result.length == matrix.length;
        @ ensures \result[0].length == matrix.length;
        @ ensures (\forall int i; 0 <= i && i < matrix.length; \result[i].length == matrix.length);
        @ ensures (\forall int i; 0 <= i && i < matrix.length; \forall int j; 0 <= j && j < matrix[0].length; \result[j][i] == matrix[i][j]);
        @
    */
    {
        int m, n, p, q;
        m = matrix.length;
        n = matrix[0].length;
        int[][] transpose = new int[n][m];

        //@ maintaining 0 <= c && c <= n;
        for (int c = 0; c < n; c++) {
            //@ maintaining 0 <= d && d <= m;
            for (int d = 0; d < m; d++) {
                transpose[c][d] = matrix[d][c];
            }
        }
        return transpose;
    }
}
