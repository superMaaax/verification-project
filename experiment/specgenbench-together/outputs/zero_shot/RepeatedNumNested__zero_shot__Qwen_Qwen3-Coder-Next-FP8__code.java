public class RepeatedNumNested {

    /*@ spec_public @*/
    public static int repeatedNum(int[] arr) {
        // Precondition: arr is non-null
        requires arr != null;
        
        // Postcondition: returns the index of the first duplicate element's first occurrence,
        // or -1 if no duplicates exist
        ensures (\forall int i, j; 0 <= i && i < j && j < arr.length; arr[i] != arr[j]) <==> \result == -1;
        ensures (\exists int i, j; 0 <= i && i < j && j < arr.length; arr[i] == arr[j]) <==> \result >= 0;
        ensures (\exists int i, j; 0 <= i && i < j && j < arr.length && arr[i] == arr[j]; 
                 \result == i && (\forall int k, l; 0 <= k && k < l && l < \result && arr[k] == arr[l]; false));
        
        for (int i = 0; i < arr.length; ++i) {
            // Loop invariant: for all k, l such that 0 <= k < l <= i, arr[k] != arr[l]
            invariant (\forall int k, l; 0 <= k && k < l && l <= i; arr[k] != arr[l]);
            decreases arr.length - i;
            
            for (int j = i + 1; j < arr.length; ++j) {
                // Loop invariant: for all k, l such that 0 <= k < l <= i, arr[k] != arr[l]
                // and for all l such i < l < j, arr[i] != arr[l]
                invariant (\forall int k, l; 0 <= k && k < l && l <= i; arr[k] != arr[l]);
                invariant (\forall int l; i < l && l < j; arr[i] != arr[l]);
                decreases arr.length - j;
                
                if(arr[i] == arr[j])
                    // Postcondition for early return: returns i when a duplicate is found at positions i and j
                    ensures \result == i;
                    return i;
            }
        }
        // Postcondition for normal return: returns -1 when no duplicates exist
        ensures \result == -1;
        return -1;
    }
    
}
