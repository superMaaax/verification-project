import gov.nasa.jpf.vm.Verify;

public class Harness_AbsSeq {
  public static void main(String[] args) throws Exception {
    int num = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int __r = new AbsSeq().Abs(num);
  }
}
