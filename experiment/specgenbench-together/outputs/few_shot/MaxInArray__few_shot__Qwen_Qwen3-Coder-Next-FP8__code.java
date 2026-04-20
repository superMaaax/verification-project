class MaxInArray {
    //@ ensures \result == -1 <==> a.length == 0;
    //@ ensures -1 < \result ==> (\forall int i; 0 <= i && i < a.length; \result >= a[i]);
    //@ ensures -1 < \result ==> (\exists int i; 0 <= i && i < a.length; \result == a[i]);
    public int maxElementInArray(int[] a) {
        if (a.length == 0) return -1;
        int res = Integer.MIN_VALUE;
        //@ maintaining 0 <= i && i <= a.length;
        //@ maintaining (\forall int j; 0 <= j && j < i; res >= a[j]);
        //@ maintaining (\exists int j; 0 <= j && j < i; res == a[j]);
        //@ decreases a.length - i;
        for(int i = 0; i < a.length; i++) {
            res = ((a[i] > res) ? a[i] : res);
        }
        return res;
    }
}
