import gov.nasa.jpf.vm.Verify;

public class Harness_OddEven {
  public static void main(String[] args) throws Exception {
    int x = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    boolean __r = new OddEven().isEven(x);
  }
}
