class IsMonotonic {
    /*@ public normal_behavior
      @ requires nums != null;
      @ ensures \result <==> (\forall int i; 0 <= i && i < nums.length - 1;
      @                         nums[i] <= nums[i + 1])
      @              || (\forall int i; 0 <= i && i < nums.length - 1;
      @                         nums[i] >= nums[i + 1]);
      @*/
    public boolean isMonotonic(int[] nums) {
        boolean inc = true, dec = true;
        int n = nums.length;
        /*@ loop_invariant 0 <= i && i <= n - 1;
          @ loop_invariant inc == (\forall int k; 0 <= k && k < i; nums[k] <= nums[k + 1]);
          @ loop_invariant dec == (\forall int k; 0 <= k && k < i; nums[k] >= nums[k + 1]);
          @ loop_invariant n == nums.length;
          @ decreases n - i;
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
