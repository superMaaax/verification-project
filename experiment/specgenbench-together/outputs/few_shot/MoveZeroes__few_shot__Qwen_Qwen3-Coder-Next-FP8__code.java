class MoveZeroes {
    //@ ensures (\forall int i; 0 <= i && i < nums.length; nums[i] == \old(nums[i]) || nums[i] == 0);
    //@ ensures (\exists int count; 0 <= count && count <= nums.length; 
    //@   (\forall int i; 0 <= i && i < count; nums[i] != 0) &&
    //@   (\forall int i; count <= i && i < nums.length; nums[i] == 0));
    public void moveZeroes(int[] nums) {
        int n = nums.length, left = 0, right = 0;
        //@ maintaining 0 <= left && left <= right && right <= n;
        //@ maintaining (\forall int i; 0 <= i && i < left; nums[i] != 0);
        //@ maintaining (\forall int i; left <= i && i < right; nums[i] == 0);
        //@ maintaining (\exists int[] oldNums; 
        //@   (\forall int i; 0 <= i && i < nums.length; nums[i] == oldNums[i] || nums[i] == 0));
        //@ decreases n - right;
        while (right < n) {
            if (nums[right] != 0) {
                swap(nums, left, right);
                left++;
            }
            right++;
        }
    }

    //@ ensures nums[left] == \old(nums[right]) && nums[right] == \old(nums[left]);
    public void swap(int[] nums, int left, int right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }
}
