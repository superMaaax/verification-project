class MySqrt {
    //@ requires x >= 0;
    //@ ensures \result >= -1;
    //@ ensures \result >= 0 ==> (long) \result * \result <= x;
    //@ ensures \result >= 0 ==> (long) (\result + 1) * (\result + 1) > x;
    public int mySqrt(int x) {
        int l = 0, r = x, ans = -1;
        //@ maintaining 0 <= l && l <= r + 1 && r <= x;
        //@ maintaining ans >= -1 && (ans == -1 || (long) ans * ans <= x);
        //@ maintaining l <= r + 1;
        //@ decreases r - l + 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if ((long) mid * mid <= x) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ans;
    }
}