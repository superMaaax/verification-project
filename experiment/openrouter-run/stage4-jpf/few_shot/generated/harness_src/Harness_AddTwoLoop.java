import gov.nasa.jpf.vm.Verify;

public class Harness_AddTwoLoop {
  public static void main(String[] args) throws Exception {
    int a = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int b = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int __r = AddTwoLoop.add(a, b);
  }
}
