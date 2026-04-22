import gov.nasa.jpf.vm.Verify;

public class Harness_PassPillow {
  public static void main(String[] args) throws Exception {
    int n = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int time = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int __r = new PassPillow().passPillow(n, time);
  }
}
