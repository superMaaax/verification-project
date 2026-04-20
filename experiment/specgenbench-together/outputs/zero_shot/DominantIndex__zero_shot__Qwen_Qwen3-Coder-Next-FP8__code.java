class DominantIndex {
    
    /*@ spec_public @*/
    public static int dominantIndex(int[] nums) {
        // Precondition: nums is not null
        // Postcondition: Returns the index of the largest element if it is at least twice as large as every other element, otherwise returns -1
        
        int biggest_index = 0;

        // Loop invariant: For all j such that 0 <= j <= i, nums[biggest_index] >= nums[j]
        // Loop decreases: nums.length - i
        for (int i = 0; i < nums.length; i++) {
            /*@ loop_invariant 0 <= biggest_index < nums.length;
                loop_invariant \forall int j; 0 <= j && j <= i ==> nums[biggest_index] >= nums[j];
                loop_decreases nums.length - i;
            @*/
            if (nums[i] > nums[biggest_index])
                biggest_index = i;
        }

        // Loop invariant: For all j such that 0 <= j <= i, if j != biggest_index then 2 * nums[j] <= nums[biggest_index]
        // Loop decreases: nums.length - i
        for (int i = 0; i < nums.length; i++) {
            /*@ loop_invariant 0 <= biggest_index < nums.length;
                loop_invariant \forall int j; 0 <= j && j <= i && j != biggest_index ==> 2 * nums[j] <= nums[biggest_index];
                loop_decreases nums.length - i;
            @*/
            if (i != biggest_index && 2 * nums[i] > nums[biggest_index])
                return -1;
        }

        // Postcondition: The returned value is either -1 or the index of the largest element that satisfies the dominance condition
        return biggest_index;
    }
}
