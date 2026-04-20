public class IsAscending {
    /*@ spec_public @*/
    public boolean isAscending (int[] arr) {
        // Precondition: arr is not null
        requires arr != null;
        
        // Postcondition: returns true if and only if the array is strictly ascending
        ensures (\forall int i; 0 <= i && i < arr.length-1; arr[i] < arr[i+1]) <==> \result;
        
        int n = arr.length;
        if (n < 2) {
            // When array has 0 or 1 elements, it's trivially ascending
            ensures \result == true;
            return true;
        }
        
        // Loop invariant: for all indices j from 0 to i-1, arr[j] < arr[j+1]
        // This means we've verified the ascending property up to index i
        /*@ loop_invariant 0 <= i && i <= n-1 &&
            (\forall int j; 0 <= j && j < i; arr[j] < arr[j+1]);
        @*/
        
        // Loop decreases by the remaining number of comparisons (n-1-i)
        /*@ decreases n-1-i;
        @*/
        
        for (int i = 0; i < n; i++) {
            // Check if we've reached the last element (to avoid array index out of bounds)
            // Note: The original code has a potential bug - it accesses arr[i+1] when i == n-1
            // This would cause ArrayIndexOutOfBoundsException for non-empty arrays
            if (i == n-1) break;
            
            if(arr[i] >= arr[i+1])
                return false;
        }
        return true;
    }
}
