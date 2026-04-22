import gov.nasa.jpf.vm.Verify;

public class Harness_ChangeCase {
  public static void main(String[] args) throws Exception {
    char c = (char) gov.nasa.jpf.vm.Verify.getInt(32, 126);
    char __r = new ChangeCase().changeCase(c);
  }
}
