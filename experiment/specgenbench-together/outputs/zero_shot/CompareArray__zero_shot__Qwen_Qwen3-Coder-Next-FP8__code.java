class CompareArray {

    /*@ spec_public @*/
    public static /*@ pure @*/ boolean arrcmp(int[] a, int[] b) {
        requires a != null && b != null;
        ensures (\result <==> (a.length == b.length && 
                               (\forall int i; 0 <= i && i < a.length; a[i] == b[i])));
        
        if(a.length != b.length) {
            return false;
        }

        /*@ loop_invariant 0 <= i && i <= a.length;
            loop_invariant (\forall int j; 0 <= j && j < i; a[j] == b[j]);
            decreases a.length - i;
        @*/
        for(int i = 0; i < a.length; i++) {
            if(a[i] != b[i]) {
                return false;
            }
        }

        return true;
    }
}
