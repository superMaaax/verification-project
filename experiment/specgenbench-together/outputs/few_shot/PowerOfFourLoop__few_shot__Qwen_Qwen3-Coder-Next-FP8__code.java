class PowerOfFourLoop {
    //@ requires true;
    //@ ensures \result <==> (\exists int k; k >= 0 && n == (int)Math.pow(4, k));
    public boolean isPowerOfFour(int n) {
        if(n <= 0) {
            return false;
        }
        int original = n;
        int exponent = 0;
        //@ maintaining n > 0 && n == original / (int)Math.pow(4, exponent);
        //@ maintaining exponent >= 0;
        //@ decreases n;
        while (n % 4 == 0) {
            n /= 4;
            exponent++;
        }
        return n == 1;
    }
}
