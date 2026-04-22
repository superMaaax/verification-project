class MinInArray {
    //@ requires a != null;
    //@ ensures \result == -1 <==> a.length == 0;
    //@ ensures a.length > 0 ==> (\exists int i; 0 <= i && i < a.length; \result == a[i]);
    //@ ensures a.length > 0 ==> (\forall int i; 0 <= i && i < a.length; \result <= a[i]);
    public int minElementInArray(int[] a) {
        if (a.length == 0) return -1;
        int res = Integer.MIN_VALUE;
        //@ maintaining 0 <= i && i <= a.length;
        //@ maintaining res == Integer.MIN_VALUE || (\exists int j; 0 <= j && j < i; res == a[j]);
        //@ maintaining res == Integer.MIN_VALUE || (\forall int j; 0 <= j && j < i; res <= a[j]);
        //@ decreases a.length - i;
        for(int i = 0; i < a.length; i++) {
            res = ((a[i] < res) ? a[i] : res);
        }
        return res;
    }
}
