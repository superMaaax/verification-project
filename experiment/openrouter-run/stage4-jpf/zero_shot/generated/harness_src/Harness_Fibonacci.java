import gov.nasa.jpf.vm.Verify;

public class Harness_Fibonacci {
  public static void main(String[] args) throws Exception {
    int n = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int __r = Fibonacci.fibCompute(n);
  }
}
