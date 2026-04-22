import gov.nasa.jpf.vm.Verify;

public class Harness_DivisorGame {
  public static void main(String[] args) throws Exception {
    int n = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    boolean __r = new DivisorGame().divisorGame(n);
  }
}
