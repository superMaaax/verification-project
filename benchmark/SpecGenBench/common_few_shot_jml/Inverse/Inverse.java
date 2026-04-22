public class Inverse {

    //@ requires x != null && y != null;
    //@ ensures x.length != y.length ==> !\result;
    //@ ensures \result ==> (\forall int k; 0 <= k && k < x.length; x[k] == y[x.length - 1 - k]);
    public static boolean Inverse(int[] x, int[] y) {
        if (x.length != y.length) return false;
        int index = 0;

        //@ maintaining 0 <= index && index <= x.length;
        //@ maintaining (\forall int k; 0 <= k && k < index; x[k] == y[x.length - 1 - k]);
        //@ decreases x.length - index;
        while (index < x.length) {
            if (x[index] != y[x.length - 1 - index]) {
                return false;
            } else {
                index = index + 1;
            }
        }
        return true;
    }
}
