import gov.nasa.jpf.vm.Verify;

public class Harness_CompareArray {
  public static void main(String[] args) throws Exception {
    int len0 = gov.nasa.jpf.vm.Verify.getInt(0, 4);
    int[] a = new int[len0];
    for (int __i0 = 0; __i0 < len0; __i0++) {
      a[__i0] = gov.nasa.jpf.vm.Verify.getInt(-3, 3);
    }
    int len1 = gov.nasa.jpf.vm.Verify.getInt(0, 4);
    int[] b = new int[len1];
    for (int __i1 = 0; __i1 < len1; __i1++) {
      b[__i1] = gov.nasa.jpf.vm.Verify.getInt(-3, 3);
    }
    boolean __r = CompareArray.arrcmp(a, b);
  }
}
