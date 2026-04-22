import gov.nasa.jpf.vm.Verify;

public class Harness_Perimeter {
  public static void main(String[] args) throws Exception {
    short x = (short) gov.nasa.jpf.vm.Verify.getInt(0, 8);
    long __r = new Perimeter().Perimeter(x);
  }
}
