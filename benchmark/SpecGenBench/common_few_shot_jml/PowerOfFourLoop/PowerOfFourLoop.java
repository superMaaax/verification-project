class PowerOfFourLoop {
    //@ ensures n <= 0 ==> !\result;
    public boolean isPowerOfFour(int n) {
        if(n <= 0) {
            return false;
        }
        //@ maintaining n >= 1;
        //@ decreases n;
        while (n % 4 == 0) {
            n /= 4;
        }
        return n == 1;
    }
}
