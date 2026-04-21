import java.util.Arrays;

public class ContainsDuplicate {

    //@ requires nums != null;
    //@ ensures \result <==> (\exists int p, q; 0 <= p && p < q && q < \old(nums.length); \old(nums[p]) == \old(nums[q]));
    public boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;

        //@ maintaining 0 <= i && i <= n - 1;
        //@ decreases (n - 1) - i;
        for (int i = 0; i < n - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                return true;
            }
        }
        return false;
    }
}
