public class SubLoop {
    /*@ spec_public @*/
    public static int subLoop(int x, int y) {
        // The method computes x - y if y > 0, or x + |y| if y <= 0
        // Effectively, it returns x - y for all integer y
        int sum = x;
        if (y > 0) {
            int n = y;
            /*@ loop_invariant n >= 0;
                loop_invariant sum == x - (y - n);
                decreases n;
            @*/
            while (n > 0) {
                sum = sum - 1;
                n = n - 1;
            }
        } else {
            int n = -y;
            /*@ loop_invariant n >= 0;
                loop_invariant sum == x + (y + n); // since y <= 0, y + n = y + (-y) = 0 initially, then increases as n decreases
                decreases n;
            @*/
            while (n > 0) {
                sum = sum + 1;
                n = n - 1;
            }
        }
        /*@ ensures \result == x - y;
        @*/
        return sum;
    }
}
