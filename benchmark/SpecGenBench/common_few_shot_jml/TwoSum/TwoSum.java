class TwoSum {
    //@ requires nums != null;
    //@ ensures (\result.length == 0) ==> (\forall int i; 0 <= i && i < nums.length; (\forall int j; i < j && j < nums.length; nums[i] + nums[j] != target));
    //@ ensures (\result.length == 2) ==> 0 <= \result[0] && \result[0] < nums.length && 0 <= \result[1] && \result[1] < nums.length && \result[0] < \result[1] && nums[\result[0]] + nums[\result[1]] == target;
    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        //@ maintaining 0 <= i && i <= n;
        //@ decreases n - i;
        for (int i = 0; i < n; ++i) {
            //@ maintaining i < j && j <= n;
            //@ decreases n - j;
            for (int j = i + 1; j < n; ++j) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }
}
