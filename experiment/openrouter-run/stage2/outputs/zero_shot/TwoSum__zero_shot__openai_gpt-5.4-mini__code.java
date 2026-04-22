class TwoSum {
    /*@ public normal_behavior
      @ requires nums != null;
      @ ensures (\result.length == 2 && 0 <= \result[0] && \result[0] < nums.length
      @          && 0 <= \result[1] && \result[1] < nums.length
      @          && \result[0] < \result[1]
      @          && nums[\result[0]] + nums[\result[1]] == target)
      @      || (\result.length == 0);
      @ assignable \nothing;
      @*/
    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        /*@ loop_invariant 0 <= i && i <= n;
          @ loop_invariant n == nums.length;
          @ loop_invariant (\forall int p; 0 <= p && p < i;
          @                    (\forall int q; p + 1 <= q && q < n;
          @                       nums[p] + nums[q] != target));
          @ decreases n - i;
          @*/
        for (int i = 0; i < n; ++i) {
            /*@ loop_invariant i + 1 <= j && j <= n;
              @ loop_invariant n == nums.length;
              @ loop_invariant (\forall int q; i + 1 <= q && q < j;
              @                    nums[i] + nums[q] != target);
              @ decreases n - j;
              @*/
            for (int j = i + 1; j < n; ++j) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }
}
