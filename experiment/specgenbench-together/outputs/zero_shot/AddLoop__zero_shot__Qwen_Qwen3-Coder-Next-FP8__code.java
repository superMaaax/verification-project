public class AddLoop {
    /*@ spec_public @*/
    public static int AddLoop(int x, int y) {
        // The method computes x + y by incrementing or decrementing sum in a loop
        /*@ requires true;
            ensures \result == x + y;
        @*/
        int sum = x;
        if (y > 0) {
            int n = y;
            /*@ loop_invariant n >= 0 && sum == x + (y - n);
                decreases n;
            @*/
            while (n > 0) {
                sum = sum + 1;
                n = n - 1;
            }
        } else {
            int n = -y;
            /*@ loop_invariant n >= 0 && sum == x - (y + n); // since y <= 0, -y = n => y = -n
                decreases n;
            @*/
            while (n > 0) {
                sum = sum - 1;
                n = n - 1;
            }
        }
        return sum;
    }
}
