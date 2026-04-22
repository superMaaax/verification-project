import gov.nasa.jpf.vm.Verify;

public class Harness_SubLoop {
  public static void main(String[] args) throws Exception {
    int x = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int y = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int __r = SubLoop.subLoop(x, y);
  }
}
