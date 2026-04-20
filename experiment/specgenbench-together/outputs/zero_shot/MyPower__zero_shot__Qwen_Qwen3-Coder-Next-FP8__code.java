class MyPower {
    /*@ spec_public @*/
    public static /*@ pure @*/ int power(int x, int n) 
        requires n >= 0;
        ensures \result == (n == 0 ? 1 : x^n);
        ensures \result == (n >= 0 ? (int)Math.pow(x, n) : \result);
    {
        int res = 1;
        /*@ loop_invariant res == (int)Math.pow(x, i);
          @ loop_invariant res >= 1;
          @ decreases n - i;
          @*/
        for(int i = 0; i < n; i++)
            res = res * x;
        return res;
    }
}
