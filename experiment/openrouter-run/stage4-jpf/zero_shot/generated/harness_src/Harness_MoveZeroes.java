import gov.nasa.jpf.vm.Verify;

public class Harness_MoveZeroes {
  public static void main(String[] args) throws Exception {
    int len0 = gov.nasa.jpf.vm.Verify.getInt(0, 4);
    int[] nums = new int[len0];
    for (int __i0 = 0; __i0 < len0; __i0++) {
      nums[__i0] = gov.nasa.jpf.vm.Verify.getInt(-3, 3);
    }
    new MoveZeroes().moveZeroes(nums);
  }
}
