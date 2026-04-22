public class IsDescending {
    /*@ public normal_behavior
      @ requires arr != null;
      @ ensures \result <==> (\forall int i; 0 <= i && i < arr.length - 1; arr[i] > arr[i+1]);
      @ assignable \nothing;
      @*/
    public boolean isDescending (int[] arr) {
        int n = arr.length;
        if (n < 2) {
            return true;
        }
        /*@
          @ loop_invariant 0 <= i && i <= n;
          @ loop_invariant (\forall int k; 0 <= k && k < i; arr[k] > arr[k+1]);
          @ decreases n - i;
          @*/
        for (int i = 0; i < n; i++) {
            if(arr[i] <= arr[i+1])
                return false;
        }
        return true;
    }
}
