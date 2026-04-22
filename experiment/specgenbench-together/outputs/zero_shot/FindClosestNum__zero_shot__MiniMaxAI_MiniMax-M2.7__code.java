class FindClosestNum {
    
    /*@
      @ requires nums != null;
      @ ensures (\forall int i; 0 <= i && i < nums.length; 
      @          \abs(\result) <= \abs(nums[i]));
      @ ensures (\exists int i; 0 <= i && i < nums.length; 
      @          nums[i] == \result);
      @ ensures (\forall int i; 0 <= i && i < nums.length; 
      @          \abs(nums[i]) == \abs(\result) ==> nums[i] <= \result);
      @*/
    public int findClosestNumber(int[] nums) {
        int ans = Integer.MAX_VALUE;
        
        /*@
          @ maintaining 0 <= i && i <= nums.length;
          @ maintaining (\forall int j; 0 <= j && j < i; 
          @              \abs(ans) <= \abs(nums[j]));
          @ maintaining (\exists int j; 0 <= j && j < i; 
          @              nums[j] == ans);
          @ maintaining (\forall int j; 0 <= j && j < i; 
          @              \abs(nums[j]) == \abs(ans) ==> nums[j] <= ans);
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
