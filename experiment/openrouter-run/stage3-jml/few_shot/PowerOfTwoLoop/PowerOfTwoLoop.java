class PowerOfTwoLoop {
    //@ requires true;
    //@ ensures \result <==> (\exists int k; k >= 0 && n == (1 << k));
    public boolean isPowerOfTwo(int n) {
        if(n <= 0) {
            return false;
        }
        //@ decreases n;
        //@ maintaining n > 0;
        while (n % 2 == 0) {
            n /= 2;
        }
        return n == 1;
    }
}
