class PowerOfTwoBranch {
    //@ ensures \result <==> (n > 0 && (\exists int k; k >= 0 && n == (1 << k)));
    public boolean isPowerOfTwo(int n) {
        if (n <= 0) {
            return false;
        }
        if ((n & (n - 1)) != 0) {
            return false;
        }
        return true;
    }
}
