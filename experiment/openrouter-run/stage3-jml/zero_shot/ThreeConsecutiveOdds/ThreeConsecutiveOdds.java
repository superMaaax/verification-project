public class ThreeConsecutiveOdds {

    /*@ public normal_behavior
      @ requires arr != null;
      @ ensures \result <==> (\exists int i; 0 <= i && i + 2 < arr.length;
      @                          (arr[i] & 1) != 0 &&
      @                          (arr[i + 1] & 1) != 0 &&
      @                          (arr[i + 2] & 1) != 0);
      @*/
    public boolean threeConsecutiveOdds(int[] arr) {
        int n = arr.length;

        /*@ loop_invariant 0 <= i && i <= n - 2;
          @ loop_invariant n == arr.length;
          @ loop_invariant (\forall int j; 0 <= j && j < i;
          @                    !((arr[j] & 1) != 0 &&
          @                      (arr[j + 1] & 1) != 0 &&
          @                      (arr[j + 2] & 1) != 0));
          @ decreases n - 2 - i;
          @*/
        for (int i = 0; i <= n - 3; ++i) {
            if ((arr[i] & 1) != 0 && (arr[i + 1] & 1) != 0 && (arr[i + 2] & 1) != 0) {
                return true;
            }
        }
        return false;
    }
}
