public class Smallest {

    /*@ spec_public @*/
    static public int Smallest(int[] a) {
        // Precondition: array must be non-null
        requires a != null;
        
        // Postcondition: returns -1 if array is empty, otherwise returns index of smallest element
        ensures (\result == -1) <==> (a.length == 0);
        ensures (\result != -1) ==> (0 <= \result && \result < a.length);
        ensures (\result != -1) ==> (\forall int i; 0 <= i && i < a.length; a[\result] <= a[i]);
        
        if (a.length == 0) return -1;

        int index = 0;
        int smallest = 0;

        /*@ loop_invariant 0 <= index && index <= a.length;
            @ loop_invariant 0 <= smallest && smallest < index;
            @ loop_invariant (\forall int i; 0 <= i && i < index; a[smallest] <= a[i]);
            @ loop_decreases a.length - index;
            @*/
        while (a.length - index > 0) {
            if (a[index] < a[smallest]) {
                smallest = index;
            }
            index = index + 1;
        }
        return smallest;
    }
}
