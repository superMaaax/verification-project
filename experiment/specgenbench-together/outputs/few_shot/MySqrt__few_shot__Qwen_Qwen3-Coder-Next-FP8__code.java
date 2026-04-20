class MySqrt {
    //@ requires x >= 0;
    //@ ensures \result >= 0;
    //@ ensures (\result * \result <= x) && ((\result + 1) * (\result + 1) > x || \result + 1 > x);
    public int mySqrt(int x) {
        int l = 0, r = x, ans = -1;
        //@ maintaining 0 <= l && l <= r + 1 && r <= x;
        //@ maintaining ans == -1 || (ans * ans <= x);
        //@ maintaining (\forall int i; ans < i && i <= r + 1; i * i > x);
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
