import gov.nasa.jpf.vm.Verify;

public class Harness_GCD {
  public static void main(String[] args) throws Exception {
    int num1 = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int num2 = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int __r = GCD.gcd(num1, num2);
  }
}
