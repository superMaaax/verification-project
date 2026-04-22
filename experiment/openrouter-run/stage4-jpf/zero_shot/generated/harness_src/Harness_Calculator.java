import gov.nasa.jpf.vm.Verify;

public class Harness_Calculator {
  public static void main(String[] args) throws Exception {
    int num1 = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int num2 = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    char operator = (char) gov.nasa.jpf.vm.Verify.getInt(32, 126);
    int __r = new Calculator().calculate(num1, num2, operator);
  }
}
