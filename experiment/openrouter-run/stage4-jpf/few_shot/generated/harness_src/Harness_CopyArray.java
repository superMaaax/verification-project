import gov.nasa.jpf.vm.Verify;

public class Harness_CopyArray {
  public static void main(String[] args) throws Exception {
    int len0 = gov.nasa.jpf.vm.Verify.getInt(0, 4);
    int[] b = new int[len0];
    for (int __i0 = 0; __i0 < len0; __i0++) {
      b[__i0] = gov.nasa.jpf.vm.Verify.getInt(-3, 3);
    }
    int iBegin = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int iEnd = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int len3 = gov.nasa.jpf.vm.Verify.getInt(0, 4);
    int[] a = new int[len3];
    for (int __i3 = 0; __i3 < len3; __i3++) {
      a[__i3] = gov.nasa.jpf.vm.Verify.getInt(-3, 3);
    }
    CopyArray.CopyArray(b, iBegin, iEnd, a);
  }
}
