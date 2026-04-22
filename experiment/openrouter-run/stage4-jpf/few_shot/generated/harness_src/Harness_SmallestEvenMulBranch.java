import gov.nasa.jpf.vm.Verify;

public class Harness_SmallestEvenMulBranch {
  public static void main(String[] args) throws Exception {
    int n = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int __r = new SmallestEvenMulBranch().smallestEvenMultiple(n);
  }
}
