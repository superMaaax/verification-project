import gov.nasa.jpf.vm.Verify;

public class Harness_ConvertToTitle {
  public static void main(String[] args) throws Exception {
    int columnNumber = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    String __r = new ConvertToTitle().convertToTitle(columnNumber);
  }
}
