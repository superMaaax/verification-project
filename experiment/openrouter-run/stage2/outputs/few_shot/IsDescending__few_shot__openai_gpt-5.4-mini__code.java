public class IsDescending {
    //@ requires arr != null;
    //@ ensures \result <==> (\forall int i; 0 <= i && i < arr.length - 1; arr[i] > arr[i+1]);
    public boolean isDescending (int[] arr) {
        int n = arr.length;
        if (n < 2) {
            return true;
        }
        //@ maintaining 0 <= i && i <= n - 1;
        //@ maintaining (\forall int j; 0 <= j && j < i; arr[j] > arr[j+1]);
        //@ decreases n - 1 - i;
        for (int i = 0; i < n; i++) {
            if(arr[i] <= arr[i+1])
                return false;
        }
        return true;
    }
}
