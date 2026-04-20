class FindClosestNum {
    
    /*@ spec_public @*/
    public int findClosestNumber(int[] nums) {
        // Precondition: nums is non-null
        requires nums != null;
        
        // Postcondition: The returned value is the element in nums with the smallest absolute value.
        // If multiple elements have the same smallest absolute value, the largest value among them is returned.
        ensures (\forall int i; 0 <= i && i < nums.length; 
            Math.abs(nums[i]) >= Math.abs(\result) || 
            (Math.abs(nums[i]) == Math.abs(\result) && nums[i] <= \result));
        
        int ans = Integer.MAX_VALUE;

        // Loop invariant: 
        // 1. ans is one of the elements processed so far (or Integer.MAX_VALUE initially)
        // 2. For all processed indices j (0 <= j <= i), 
        //    Math.abs(nums[j]) >= Math.abs(ans) OR 
        //    (Math.abs(nums[j]) == Math.abs(ans) && nums[j] <= ans)
        /*@ loop_invariant 
            (0 <= i && i < nums.length) && 
            (\forall int j; 0 <= j && j <= i; 
                Math.abs(nums[j]) >= Math.abs(ans) || 
                (Math.abs(nums[j]) == Math.abs(ans) && nums[j] <= ans));
        @*/
        
        // Loop variant: The number of remaining iterations (nums.length - i - 1)
        /*@ decreases nums.length - i - 1; @*/
        
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
