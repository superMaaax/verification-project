import gov.nasa.jpf.vm.Verify;

public class Harness_LCM {
  public static void main(String[] args) throws Exception {
    int num1 = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int num2 = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int __r = LCM.lcm(num1, num2);
  }
}
