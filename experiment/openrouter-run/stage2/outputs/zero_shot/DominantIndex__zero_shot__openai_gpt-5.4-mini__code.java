class DominantIndex {
    
    /*@
      @ requires nums != null;
      @ ensures (\result == -1) || (\result >= 0 && \result < nums.length);
      @ ensures \result != -1 ==>
      @     (\forall int i; 0 <= i && i < nums.length && i != \result;
      @         2 * nums[i] <= nums[\result]);
      @*/
    public static int dominantIndex(int[] nums) {
        int biggest_index = 0;

        /*@
          @ loop_invariant 0 <= i && i <= nums.length;
          @ loop_invariant 0 <= biggest_index && biggest_index < nums.length;
          @ loop_invariant (\forall int k; 0 <= k && k < i; nums[biggest_index] >= nums[k]);
          @ decreases nums.length - i;
          @*/
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > nums[biggest_index])
                biggest_index = i;
        }

        /*@
          @ loop_invariant 0 <= i && i <= nums.length;
          @ loop_invariant 0 <= biggest_index && biggest_index < nums.length;
          @ loop_invariant (\forall int k; 0 <= k && k < i && k != biggest_index;
          @                     2 * nums[k] <= nums[biggest_index]);
          @ decreases nums.length - i;
          @*/
        for (int i = 0; i < nums.length; i++) {
            if (i != biggest_index && 2 * nums[i] > nums[biggest_index])
                return -1;
        }

        return biggest_index;
    }
}
