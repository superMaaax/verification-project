class FindClosestNum {
    
    //@ requires nums != null;
    //@ ensures (\exists int i; 0 <= i && i < nums.length; \result == nums[i]);
    //@ ensures nums.length > 0 ==> (\forall int i; 0 <= i && i < nums.length; 
    //@                             (\result < 0 ? -\result : \result) <= (nums[i] < 0 ? -nums[i] : nums[i]));
    //@ ensures nums.length > 0 ==> (\forall int i; 0 <= i && i < nums.length;
    //@                             ((\result < 0 ? -\result : \result) == (nums[i] < 0 ? -nums[i] : nums[i])) ==> \result >= nums[i]);
    public int findClosestNumber(int[] nums) {
        int ans = Integer.MAX_VALUE;

        //@ maintaining 0 <= i && i <= nums.length;
        //@ maintaining ans == Integer.MAX_VALUE || (\exists int j; 0 <= j && j < i; ans == nums[j]);
        //@ maintaining ans == Integer.MAX_VALUE || (\forall int j; 0 <= j && j < i;
        //@                             (ans < 0 ? -ans : ans) <= (nums[j] < 0 ? -nums[j] : nums[j]));
        //@ maintaining ans == Integer.MAX_VALUE || (\forall int j; 0 <= j && j < i;
        //@                             ((ans < 0 ? -ans : ans) == (nums[j] < 0 ? -nums[j] : nums[j])) ==> ans >= nums[j]);
        //@ decreases nums.length - i;
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
