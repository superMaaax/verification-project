class MaxInArray {
    /*@ public normal_behavior
      @   requires a != null;
      @   ensures (a.length == 0) ==> (\result == -1);
      @   ensures (a.length > 0) ==> (\result >= a[0] && (\forall int i; 0 <= i && i < a.length; a[i] <= \result));
      @*/
    public int maxElementInArray(int[] a) {
        if (a.length == 0) return -1;
        int res = Integer.MIN_VALUE;
        /*@ loop_invariant 0 <= i && i <= a.length;
          @ loop_invariant (i > 0) ==> (\forall int j; 0 <= j && j < i; a[j] <= res);
          @ loop_invariant (i == 0) ==> (res == Integer.MIN_VALUE);
          @ decreases a.length - i;
          @*/
        for(int i = 0; i < a.length; i++) {
            res = ((a[i] > res) ? a[i] : res);
        }
        return res;
    }
}
