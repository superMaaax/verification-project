class FindClosestNum {
    
    /*@
      @ public normal_behavior
      @ requires nums != null && nums.length > 0;
      @ ensures (\exists int i; 0 <= i && i < nums.length;
      @             \result == nums[i] &&
      @             (\forall int j; 0 <= j && j < nums.length;
      @                 (\let int absRes = (\result < 0 ? -\result : \result);
      @                  \let int absJ = (nums[j] < 0 ? -nums[j] : nums[j]);
      @                  absRes > absJ || (absRes == absJ && \result >= nums[j]))));
      @*/
    public int findClosestNumber(int[] nums) {
        int ans = Integer.MAX_VALUE;

        /*@
          @ loop_invariant 0 <= i && i <= nums.length;
          @ loop_invariant (\forall int k; 0 <= k && k < i;
          @                    (\let int absAns = (ans < 0 ? -ans : ans);
          @                     \let int absK = (nums[k] < 0 ? -nums[k] : nums[k]);
          @                     absAns < absK || (absAns == absK && ans >= nums[k])));
          @ decreases nums.length - i;
          @*/
        for(int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int absNum = (num<0?-num:num);
            int absAns = (ans<0?-ans:ans);
            if(absNum < absAns || (absNum == absAns && num > ans)) {
                ans = num;
            }
        }

        return ans;
    }
}
