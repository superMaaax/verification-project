import gov.nasa.jpf.vm.Verify;

public class Harness_Abs {
  public static void main(String[] args) throws Exception {
    int num = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int __r = new Abs().Abs(num);
  }
}
