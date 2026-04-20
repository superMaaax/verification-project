class UglyNum {
    //@ requires n != Integer.MIN_VALUE;
    //@ ensures \result <==> (\exists int k2, k3, k5; n == (int)(Math.pow(2, k2) * Math.pow(3, k3) * Math.pow(5, k5)) && k2 >= 0 && k3 >= 0 && k5 >= 0);
    public static boolean isUgly(int n) {
        if (n <= 0) {
            return false;
        }
        int[] factors = {2, 3, 5};
        //@ maintaining 0 <= i && i <= factors.length;
        for (int i = 0; i < factors.length; i++) {
            int factor = factors[i];
            //@ maintaining n > 0;
            while (n % factor == 0) {
                n /= factor;
            }
        }
        return n == 1;
    }
}
