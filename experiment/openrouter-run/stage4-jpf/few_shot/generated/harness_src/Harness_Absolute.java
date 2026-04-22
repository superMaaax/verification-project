import gov.nasa.jpf.vm.Verify;

public class Harness_Absolute {
  public static void main(String[] args) throws Exception {
    int num = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int __r = new Absolute().Absolute(num);
  }
}
