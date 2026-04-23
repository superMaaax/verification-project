public class SubLoop {
    //@ requires y != Integer.MIN_VALUE;
    //@ ensures \result == x - y;
    public static int subLoop(int x, int y) {
        int sum = x;
        if (y > 0) {
            int n = y;
            //@ maintaining 0 <= n && n <= y && sum == x - (y - n);
            //@ decreases n;
            while (n > 0) {
                sum = sum - 1;
                n = n - 1;
            }
        } else {
            int n = -y;
            //@ maintaining 0 <= n && n <= -y && sum == x + ((-y) - n);
            //@ decreases n;
            while (n > 0) {
                sum = sum + 1;
                n = n - 1;
            }
        }
        return sum;
    }
}
