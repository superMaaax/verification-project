import gov.nasa.jpf.vm.Verify;

public class Harness_NotCommonFactor {
  public static void main(String[] args) throws Exception {
    int a = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int b = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int factor = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    boolean __r = new NotCommonFactor().notCommonFactor(a, b, factor);
  }
}
