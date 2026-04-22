import gov.nasa.jpf.vm.Verify;

public class Harness_NegAbs {
  public static void main(String[] args) throws Exception {
    int num = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int __r = new NegAbs().negAbs(num);
  }
}
