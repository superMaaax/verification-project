class PowerOfTwo {
    //@ ensures \result <==> (n > 0 && (\exists int k; k >= 0 && n == (1 << k)));
    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }
}
