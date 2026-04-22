class DominantIndex {
    
    /*@
      @ requires nums != null;
      @ ensures \result == -1 ==> 
      @     (\forall int i; i >= 0 && i < nums.length; 
      @         nums[i] * 2 <= \old(nums[\old(\result)]));
      @ ensures \result != -1 ==>
      @     (\forall int i; i >= 0 && i < nums.length && i != \result;
      @         nums[\result] > 2 * nums[i]);
      @*/
    public static int dominantIndex(int[] nums) {
        int biggest_index = 0;

        /*@
          @ maintaining (\forall int j; j >= 0 && j < i; 
          @     nums[j] <= nums[biggest_index]);
          @ decreases nums.length - i;
          @*/
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > nums[biggest_index])
                biggest_index = i;
        }

        /*@
          @ maintaining (\forall int j; j >= 0 && j < i && j != biggest_index;
          @     nums[biggest_index] > 2 * nums[j]);
          @ decreases nums.length - i;
          @*/
        for (int i = 0; i < nums.length; i++) {
            if (i != biggest_index && 2 * nums[i] > nums[biggest_index])
                return -1;
        }

        return biggest_index;
    }
}
