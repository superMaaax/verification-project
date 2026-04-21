class FindClosestNum {
    
    //@ requires nums != null;
    //@ ensures nums.length == 0 ==> \result == Integer.MAX_VALUE;
    //@ ensures nums.length > 0 ==> (\exists int k; 0 <= k && k < nums.length; nums[k] == \result);
    public int findClosestNumber(int[] nums) {
        int ans = Integer.MAX_VALUE;

        //@ maintaining 0 <= i && i <= nums.length;
        //@ maintaining nums.length == 0 ==> ans == Integer.MAX_VALUE;
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