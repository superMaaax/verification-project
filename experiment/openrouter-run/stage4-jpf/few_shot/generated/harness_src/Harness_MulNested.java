import gov.nasa.jpf.vm.Verify;

public class Harness_MulNested {
  public static void main(String[] args) throws Exception {
    int x = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int y = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int __r = MulNested.mulNested(x, y);
  }
}
