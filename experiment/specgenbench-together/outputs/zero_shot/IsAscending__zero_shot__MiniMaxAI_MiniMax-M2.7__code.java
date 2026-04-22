public class IsAscending {
    /*@ requires arr != null;
        ensures \result == true ==> 
            (\forall int i; 0 <= i < arr.length - 1; arr[i] < arr[i+1]);
      @*/
    public boolean isAscending (int[] arr) {
        int n = arr.length;
        if (n < 2) {
            return true;
        }
        /*@ maintaining (\forall int j; 0 <= j < i; arr[j] < arr[j+1]);
            decreases n - i;
          @*/
        for (int i = 0; i < n; i++) {
            if(arr[i] >= arr[i+1])
                return false;
        }
        return true;
    }
}
