import java.util.Arrays;

public class ContainsDuplicate {

    /*@ spec_public @*/
    public boolean containsDuplicate(int[] nums) {
        requires nums != null;
        ensures (\forall int i; 0 <= i && i < nums.length - 1; nums[i] != nums[i + 1]) <==> \result == false;
        ensures (\exists int i; 0 <= i && i < nums.length - 1; nums[i] == nums[i + 1]) <==> \result == true;

        Arrays.sort(nums);
        int n = nums.length;

        //@ loop_invariant 0 <= i && i <= n - 2;
        //@ loop_invariant (\forall int j; 0 <= j && j < i; nums[j] != nums[j + 1]);
        //@ loop_decreases n - 1 - i;
        for (int i = 0; i < n - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                ensures \result == true;
                return true;
            }
        }
        ensures \result == false;
        return false;
    }
}
