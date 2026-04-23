public class ReLUSeq {
    //@ ensures x >= 0.0 ==> \result == x;
    //@ ensures x < 0.0 ==> \result == 0.0;
    public static double computeReLU(double x) {
        return ((x >= 0) ? x : 0);
    }
}