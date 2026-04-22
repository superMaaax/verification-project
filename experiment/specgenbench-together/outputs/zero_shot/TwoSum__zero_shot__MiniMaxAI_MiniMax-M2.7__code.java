class TwoSum {
    /*@ requires nums != null;
        ensures \result != null && \result.length == 2 ==> 
                 (0 <= \result[0] && \result[0] < nums.length &&
                  0 <= \result[1] && \result[1] < nums.length &&
                  nums[\result[0]] + nums[\result[1]] == target);
        ensures \result != null && \result.length == 0 ==> 
                 (\forall int i; 0 <= i && i < nums.length;
                  \forall int j; i < j && j < nums.length;
                  nums[i] + nums[j] != target);
     @*/
    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        for (int i = 0; i < n; ++i) {
            //@ loop_invariant 0 <= i && i <= n;
            //@ decreases n - i;
            for (int j = i + 1; j < n; ++j) {
                //@ loop_invariant i + 1 <= j && j <= n;
                //@ decreases n - j;
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }
}
