public class ReverseArray {
    //@ requires original != null;
    //@ ensures \result != null && \result.length == original.length;
    //@ ensures (\forall int i; 0 <= i && i < original.length; \result[i] == original[original.length - 1 - i]);
    public static int[] reverse(int[] original) {
        int n = original.length;
        int[] res = new int[n];
        //@ maintaining 0 <= i && i <= n;
        //@ maintaining (\forall int k; 0 <= k && k < i; res[k] == original[n - 1 - k]);
        //@ decreases n - i;
        for(int i = 0; i < n ; i++) {
            res[i] = original[n - i - 1];
        }
        return res;
    }
}
