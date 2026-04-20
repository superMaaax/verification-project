class RotateArray {
    /*@ spec_public @*/ public static void rotate(int[] nums, int k) {
        requires nums != null;
        requires k >= 0;
        ensures (\forall int i; 0 <= i && i < nums.length; 
                 nums[i] == \old(nums[(i - k % nums.length + nums.length) % nums.length]));
        ensures nums.length == \old(nums.length);
        
        int n = nums.length;
        int[] newArr = new int[n];
        /*@ loop_invariant 0 <= i && i <= n;
            loop_invariant (\forall int j; 0 <= j && j < i; 
                           newArr[(j + k) % n] == \old(nums[j]));
            decreases n - i;
        @*/
        for (int i = 0; i < n; ++i) {
            newArr[(i + k) % n] = nums[i];
        }
        System.arraycopy(newArr, 0, nums, 0, n);
    }
}
