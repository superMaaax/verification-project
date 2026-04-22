import gov.nasa.jpf.vm.Verify;

public class Harness_LeapYearSeq {
  public static void main(String[] args) throws Exception {
    int year = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    boolean __r = new LeapYearSeq().isLeapYear(year);
  }
}
