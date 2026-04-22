import gov.nasa.jpf.vm.Verify;

public class Harness_PrimeCheck {
  public static void main(String[] args) throws Exception {
    int a = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    boolean __r = new PrimeCheck().isPrime(a);
  }
}
