class CompareArray {

    //@ requires a != null && b != null;
    //@ ensures \result <==> a.length == b.length && (\forall int i; 0 <= i && i < a.length; a[i] == b[i]);
    public static boolean arrcmp(int[] a, int[] b) {
        if(a.length != b.length) {
	    return false;
        }

        //@ maintaining 0 <= i && i <= a.length;
        //@ maintaining (\forall int k; 0 <= k && k < i; a[k] == b[k]);
        //@ decreases a.length - i;
        for(int i = 0; i < a.length; i++) {
            if(a[i] != b[i]) {
                return false;
            }
        }

        return true;
    }
}
