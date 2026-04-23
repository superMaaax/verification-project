class MaxInArray {
    //@ requires a != null;
    //@ ensures a.length == 0 ==> \result == -1;
    //@ ensures a.length > 0 ==> (\forall int k; 0 <= k && k < a.length; a[k] <= \result);
    //@ ensures a.length > 0 ==> (\exists int k; 0 <= k && k < a.length; a[k] == \result);
    public int maxElementInArray(int[] a) {
        if (a.length == 0) return -1;
        int res = Integer.MIN_VALUE;
        //@ maintaining 0 <= i && i <= a.length;
        //@ maintaining a.length > 0 ==> (\forall int k; 0 <= k && k < i; a[k] <= res);
        //@ decreases a.length - i;
        for(int i = 0; i < a.length; i++) {
            res = ((a[i] > res) ? a[i] : res);
        }
        return res;
    }
}
