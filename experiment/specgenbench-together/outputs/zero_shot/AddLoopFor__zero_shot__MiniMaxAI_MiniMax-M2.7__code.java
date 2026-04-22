public class AddLoopFor {
    /*@ spec_public @*/ int sum;

    //@ requires true;
    //@ ensures \result == y + x;
    public static int addLoop(int x, int y) {
        int sum = y;
        if (x > 0) {
            for(int n = x; n > 0; n = n - 1) {
                //@ maintaining sum == y + (x - n);
                //@ decreases n;
                sum = sum + 1;
            }
        } else {
            for(int n = -x; n > 0; n = n - 1) {
                //@ maintaining sum == y + x + n;
                //@ decreases n;
                sum = sum - 1;
            }
        }
        return sum;
    }
}
