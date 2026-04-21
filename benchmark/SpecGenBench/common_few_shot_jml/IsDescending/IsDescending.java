public class IsDescending {
    //@ requires arr != null;
    //@ ensures arr.length < 2 ==> \result;
    public boolean isDescending (int[] arr) {
        int n = arr.length;
        if (n < 2) {
            return true;
        }
        //@ maintaining 0 <= i && i <= n;
        //@ maintaining (\forall int k; 0 <= k && k < i; arr[k] > arr[k + 1]);
        //@ decreases n - i;
        for (int i = 0; i < n; i++) {
            if(arr[i] <= arr[i+1])
                return false;
        }
        return true;
    }
}