/*@ class IsMonotonic {
  @   represents static long nondet <- \random;
  @ }
  @*/
class IsMonotonic {
    /*@ requires nums != null;
      @ ensures \result == true ==> 
      @         ((\forall int k; 0 <= k < nums.length - 1; nums[k] <= nums[k+1]) ||
      @          (\forall int k; 0 <= k < nums.length - 1; nums[k] >= nums[k+1]));
      @*/
    public boolean isMonotonic(int[] nums) {
        boolean inc = true, dec = true;
        int n = nums.length;
        /*@ maintaining (inc ==> (\forall int k; 0 <= k <= i; nums[k] <= nums[k+1])) &&
          @             (dec ==> (\forall int k; 0 <= k <= i; nums[k] >= nums[k+1]));
          @ decreases n - 1 - i;
          @*/
        for (int i = 0; i < n - 1; ++i) {
            if (nums[i] > nums[i + 1]) {
                inc = false;
            }
            if (nums[i] < nums[i + 1]) {
                dec = false;
            }
        }
        return inc || dec;
    }
}
