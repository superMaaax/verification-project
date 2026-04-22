import gov.nasa.jpf.vm.Verify;

public class Harness_IntCube {
  public static void main(String[] args) throws Exception {
    int x = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int __r = IntCube.cubeOf(x);
  }
}
