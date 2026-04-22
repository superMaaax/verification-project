import gov.nasa.jpf.vm.Verify;

public class Harness_NotCommonMultiple {
  public static void main(String[] args) throws Exception {
    int a = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int b = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int m = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    boolean __r = new NotCommonMultiple().NotCommonMultiple(a, b, m);
  }
}
