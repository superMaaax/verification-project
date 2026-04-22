public class ReLUSeq {
    /*@ requires true;
      @ ensures \result == (x >= 0 ? x : 0);
      @*/
    public static double computeReLU(double x) {
        return ((x >= 0) ? x : 0);
    }
}
