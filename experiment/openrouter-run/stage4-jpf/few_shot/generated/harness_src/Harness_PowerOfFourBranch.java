import gov.nasa.jpf.vm.Verify;

public class Harness_PowerOfFourBranch {
  public static void main(String[] args) throws Exception {
    int n = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    boolean __r = new PowerOfFourBranch().isPowerOfFour(n);
  }
}
