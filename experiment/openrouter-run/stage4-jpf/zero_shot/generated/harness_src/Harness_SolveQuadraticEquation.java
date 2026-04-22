import gov.nasa.jpf.vm.Verify;

public class Harness_SolveQuadraticEquation {
  public static void main(String[] args) throws Exception {
    int a = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int b = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int c = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    double[] __r = SolveQuadraticEquation.solve(a, b, c);
  }
}
