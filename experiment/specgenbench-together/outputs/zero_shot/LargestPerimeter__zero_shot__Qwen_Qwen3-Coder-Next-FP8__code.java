import java.util.Arrays;

class LargestPerimeter {
    /*@ spec_public @*/
    public int largestPerimeter(int[] A) {
        // Precondition: A is non-null
        // Postcondition: Returns the largest perimeter of a triangle that can be formed from three elements in A, or 0 if no valid triangle exists
        
        /*@ requires A != null;
            ensures (\forall int i, j, k; 0 <= i && i < j && j < k && k < A.length; 
                     !(A[i] + A[j] > A[k] && A[i] + A[k] > A[j] && A[j] + A[k] > A[i])) 
                     ==> \result == 0;
            ensures (\exists int i, j, k; 0 <= i && i < j && j < k && k < A.length;
                     A[i] + A[j] > A[k] && A[i] + A[k] > A[j] && A[j] + A[k] > A[i] &&
                     \result == A[i] + A[j] + A[k]);
            ensures (\forall int i, j, k; 0 <= i && i < j && j < k && k < A.length;
                     A[i] + A[j] > A[k] && A[i] + A[k] > A[j] && A[j] + A[k] > A[i] 
                     ==> \result >= A[i] + A[j] + A[k]);
        */
        
        Arrays.sort(A);
        
        /*@ loop_invariant 
            (\forall int m; i < m && m < A.length; 
             !(A[m-2] + A[m-1] > A[m])) &&
            (\forall int p, q, r; 
             (i < p && p < q && q < r && r < A.length) || 
             (i < p && p < q && q < A.length && r == A.length) ||
             (i < p && p < A.length && q == A.length && r == A.length) ||
             (i < A.length && p == A.length && q == A.length && r == A.length);
             !(A[p] + A[q] > A[r] && A[p] + A[r] > A[q] && A[q] + A[r] > A[p]));
            decreases i;
        */
        for (int i = A.length - 1; i >= 2; --i) {
            /*@ loop_invariant 
                (\forall int m; i < m && m < A.length; 
                 !(A[m-2] + A[m-1] > A[m]));
                decreases i;
            */
            if (A[i - 2] + A[i - 1] > A[i]) {
                /*@ ensures \result == A[i-2] + A[i-1] + A[i];
                    ensures (\forall int p, q, r; 
                             0 <= p && p < q && q < r && r < A.length;
                             A[p] + A[q] > A[r] && A[p] + A[r] > A[q] && A[q] + A[r] > A[p]
                             ==> \result >= A[p] + A[q] + A[r]);
                */
                return A[i - 2] + A[i - 1] + A[i];
            }
        }
        
        /*@ ensures \result == 0;
            ensures (\forall int p, q, r; 
                     0 <= p && p < q && q < r && r < A.length;
                     !(A[p] + A[q] > A[r] && A[p] + A[r] > A[q] && A[q] + A[r] > A[p]));
        */
        return 0;
    }
}
