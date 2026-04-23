class MoveZeroes {
    //@ requires nums != null;
    public void moveZeroes(int[] nums) {
        int n = nums.length, left = 0, right = 0;
        //@ maintaining 0 <= left && left <= right && right <= n;
        //@ maintaining (\forall int t; 0 <= t && t < left; nums[t] != 0);
        //@ maintaining (\forall int t; left <= t && t < right; nums[t] == 0);
        //@ decreases n - right;
        while (right < n) {
            if (nums[right] != 0) {
                swap(nums, left, right);
                left++;
            }
            right++;
        }
    }

    //@ requires nums != null;
    //@ requires 0 <= left && left < nums.length && 0 <= right && right < nums.length;
    //@ ensures nums[left] == \old(nums[right]) && nums[right] == \old(nums[left]);
    public void swap(int[] nums, int left, int right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }
}
