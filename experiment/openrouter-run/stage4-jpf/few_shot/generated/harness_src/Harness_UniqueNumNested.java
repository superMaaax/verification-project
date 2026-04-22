import gov.nasa.jpf.vm.Verify;

public class Harness_UniqueNumNested {
  public static void main(String[] args) throws Exception {
    int len0 = gov.nasa.jpf.vm.Verify.getInt(0, 4);
    int[] arr = new int[len0];
    for (int __i0 = 0; __i0 < len0; __i0++) {
      arr[__i0] = gov.nasa.jpf.vm.Verify.getInt(-3, 3);
    }
    int __r = UniqueNumNested.uniqueNum(arr);
  }
}
