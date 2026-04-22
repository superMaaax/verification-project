import gov.nasa.jpf.vm.Verify;

public class Harness_ReLU {
  public static void main(String[] args) throws Exception {
    double x = (double) gov.nasa.jpf.vm.Verify.getInt(-2, 2);
    double __r = ReLU.computeReLU(x);
  }
}
