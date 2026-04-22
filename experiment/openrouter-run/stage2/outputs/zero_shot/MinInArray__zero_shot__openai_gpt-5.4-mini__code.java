class MinInArray {
    /*@
      @ requires a != null;
      @ ensures (a.length == 0 ==> \result == -1);
      @ ensures (a.length > 0 ==> (\result == (\min int i; 0 <= i && i < a.length; a[i])));
      @*/
    public int minElementInArray(int[] a) {
        if (a.length == 0) return -1;
        int res = Integer.MIN_VALUE;
        /*@
          @ loop invariant 0 <= i && i <= a.length;
          @ loop invariant (i == 0 ==> res == Integer.MIN_VALUE);
          @ loop invariant (i > 0 ==> res == (\min int j; 0 <= j && j < i; a[j]));
          @ loop decreases a.length - i;
          @*/
        for(int i = 0; i < a.length; i++) {
            res = ((a[i] < res) ? a[i] : res);
        }
        return res;
    }
}
