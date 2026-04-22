import gov.nasa.jpf.vm.Verify;

public class Harness_ComputeOverlap {
  public static void main(String[] args) throws Exception {
    int ax1 = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int ay1 = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int ax2 = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int ay2 = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int bx1 = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int by1 = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int bx2 = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int by2 = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int __r = new ComputeOverlap().computeOverlapArea(ax1, ay1, ax2, ay2, bx1, by1, bx2, by2);
  }
}
