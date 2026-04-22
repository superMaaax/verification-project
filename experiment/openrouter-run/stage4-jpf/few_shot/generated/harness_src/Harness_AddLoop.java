import gov.nasa.jpf.vm.Verify;

public class Harness_AddLoop {
  public static void main(String[] args) throws Exception {
    int x = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int y = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int __r = AddLoop.AddLoop(x, y);
  }
}
