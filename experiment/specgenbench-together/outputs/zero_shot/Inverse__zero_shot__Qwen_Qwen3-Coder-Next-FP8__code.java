public class Inverse {

    /*@ spec_public @*/
    public static boolean Inverse(int[] x, int[] y) {
        requires x != null && y != null;
        requires x.length == y.length;
        ensures \result <==> (\forall int i; 0 <= i && i < x.length; x[i] == y[x.length - 1 - i]);
        
        int index = 0;

        /*@ loop_invariant 0 <= index && index <= x.length;
            loop_invariant (\forall int i; 0 <= i && i < index; x[i] == y[x.length - 1 - i]);
            decreases x.length - index;
        @*/
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
