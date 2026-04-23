class PowerOfTwoLoop {
    //@ ensures n <= 0 ==> !\result;
    public boolean isPowerOfTwo(int n) {
        if(n <= 0) {
            return false;
        }
        //@ maintaining n >= 1;
        //@ decreases n;
        while (n % 2 == 0) {
            n /= 2;
        }
        return n == 1;
    }
}
