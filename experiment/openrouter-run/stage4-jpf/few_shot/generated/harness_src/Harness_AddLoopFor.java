import gov.nasa.jpf.vm.Verify;

public class Harness_AddLoopFor {
  public static void main(String[] args) throws Exception {
    int x = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int y = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int __r = AddLoopFor.addLoop(x, y);
  }
}
