import gov.nasa.jpf.vm.Verify;

public class Harness_Conjunction {
  public static void main(String[] args) throws Exception {
    boolean b1 = gov.nasa.jpf.vm.Verify.getInt(0, 1) != 0;
    boolean b2 = gov.nasa.jpf.vm.Verify.getInt(0, 1) != 0;
    boolean __r = Conjunction.conjunctOf(b1, b2);
  }
}
