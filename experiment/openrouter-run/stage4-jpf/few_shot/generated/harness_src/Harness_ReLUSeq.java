import gov.nasa.jpf.vm.Verify;

public class Harness_ReLUSeq {
  public static void main(String[] args) throws Exception {
    double x = (double) gov.nasa.jpf.vm.Verify.getInt(-2, 2);
    double __r = ReLUSeq.computeReLU(x);
  }
}
