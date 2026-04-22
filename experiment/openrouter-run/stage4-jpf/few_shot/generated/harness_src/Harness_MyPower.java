import gov.nasa.jpf.vm.Verify;

public class Harness_MyPower {
  public static void main(String[] args) throws Exception {
    int x = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int n = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int __r = MyPower.power(x, n);
  }
}
