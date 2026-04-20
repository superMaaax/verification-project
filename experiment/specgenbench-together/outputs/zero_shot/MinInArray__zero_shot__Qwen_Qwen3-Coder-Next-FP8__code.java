class MinInArray {
    /*@ spec_public @*/
    public int minElementInArray(int[] a) {
        requires a != null;
        ensures (\forall int i; 0 <= i && i < a.length; \result <= a[i]);
        ensures (\exists int i; 0 <= i && i < a.length; \result == a[i]);
        ensures (a.length == 0) <==> (\result == -1);
        ensures (a.length > 0) ==> (\result != Integer.MIN_VALUE);
        
        if (a.length == 0) return -1;
        
        int res = Integer.MIN_VALUE;
        /*@ loop_invariant 0 <= i && i <= a.length;
            loop_invariant (\forall int j; 0 <= j && j < i; res <= a[j]);
            loop_invariant (\exists int j; 0 <= j && j < i; res == a[j]);
            loop_invariant (i == 0) ==> (res == Integer.MIN_VALUE);
            loop_invariant (i > 0) ==> (res != Integer.MIN_VALUE);
            decreases a.length - i;
        @*/
        for(int i = 0; i < a.length; i++) {
            res = ((a[i] < res) ? a[i] : res);
        }
        return res;
    }
}
