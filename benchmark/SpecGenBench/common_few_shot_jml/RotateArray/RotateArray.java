
class RotateArray {
    //@ requires nums != null && nums.length > 0;
    public static void rotate(int[] nums, int k) {
        int n = nums.length;
        int[] newArr = new int[n];
        //@ maintaining 0 <= i && i <= n;
        //@ decreases n - i;
        for (int i = 0; i < n; ++i) {
            newArr[(i + k) % n] = nums[i];
        }
        System.arraycopy(newArr, 0, nums, 0, n);
    }
}
