class MoveZeroes {
    /*@ requires nums != null;
      @ ensures \forall int i; 0 <= i < nums.length; 
      @            (nums[i] == 0 ==> \exists int j; 0 <= j < nums.length && \old(nums[j]) == 0);
      @ ensures (\sum int i; 0 <= i < nums.length; nums[i] != 0 ? 1 : 0) == 
      @         (\sum int i; 0 <= i < nums.length; \old(nums[i]) != 0 ? 1 : 0);
      @ ensures \forall int i, j; 0 <= i < j < nums.length; 
      @            (\old(nums[i]) != 0 && \old(nums[j]) != 0) ==> 
      @            (\exists int k, l; 0 <= k < l < nums.length; 
      @             nums[k] == \old(nums[i]) && nums[l] == \old(nums[j]));
      @*/
    public void moveZeroes(int[] nums) {
        int n = nums.length, left = 0, right = 0;
        /*@ maintaining 0 <= left <= right && right <= n;
          @ maintaining \forall int i; 0 <= i < left; nums[i] != 0;
          @ maintaining \forall int i; left <= i < right; nums[i] == 0;
          @ maintaining (\sum int i; 0 <= i < right; nums[i] != 0 ? 1 : 0) == left;
          @ decreases n - right;
          @*/
        while (right < n) {
            if (nums[right] != 0) {
                swap(nums, left, right);
                left++;
            }
            right++;
        }
    }

    /*@ requires nums != null;
      @ requires 0 <= left < nums.length && 0 <= right < nums.length;
      @ ensures nums[left] == \old(nums[right]) && nums[right] == \old(nums[left]);
      @*/
    public void swap(int[] nums, int left, int right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }
}
