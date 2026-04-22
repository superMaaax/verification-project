class TwoSum {
    /*@ requires nums != null;
      @ ensures (\result.length == 2 ==> 0 <= \result[0] && \result[0] < nums.length
      @                         && 0 <= \result[1] && \result[1] < nums.length
      @                         && \result[0] < \result[1]
      @                         && nums[\result[0]] + nums[\result[1]] == target);
      @ ensures (\result.length == 0 ==> (\forall int i, j; 0 <= i && i < j && j < nums.length;
      @                                   nums[i] + nums[j] != target));
      @*/
    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        /*@ maintaining 0 <= i && i <= n;
          @ maintaining (\forall int p, q; 0 <= p && p < q && p < i && q < n;
          @                              nums[p] + nums[q] != target);
          @ decreases n - i;
          @*/
        for (int i = 0; i < n; ++i) {
            /*@ maintaining i + 1 <= j && j <= n;
              @ maintaining (\forall int q; i < q && q < j; nums[i] + nums[q] != target);
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
