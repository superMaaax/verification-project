class MySqrt {
    /*@ 
      @ requires x >= 0;
      @ ensures \result >= 0;
      @ ensures (long)\result * (long)\result <= x;
      @ ensures (long)(\result + 1) * (long)(\result + 1) > x;
      @*/
    public int mySqrt(int x) {
        int l = 0, r = x, ans = -1;
        //@ loop_invariant 0 <= l && l <= r + 1 && r <= x;
        //@ loop_invariant -1 <= ans && ans <= x;
        //@ loop_invariant ans < l;
        //@ loop_invariant (long)ans * (long)ans <= x;
        //@ loop_invariant (\forall int i; ans < i && i < l; (long)i * (long)i > x);
        //@ decreases r - l;
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
