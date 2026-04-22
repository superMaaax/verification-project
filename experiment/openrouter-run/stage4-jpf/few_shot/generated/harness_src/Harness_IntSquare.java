import gov.nasa.jpf.vm.Verify;

public class Harness_IntSquare {
  public static void main(String[] args) throws Exception {
    int x = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int __r = IntSquare.squareOf(x);
  }
}
