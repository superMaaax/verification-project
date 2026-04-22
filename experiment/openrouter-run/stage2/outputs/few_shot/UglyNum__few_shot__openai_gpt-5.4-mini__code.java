class UglyNum {
    //@ ensures \result <==> (\exists int a, b, c; a >= 0 && b >= 0 && c >= 0; n == (int)(Math.pow(2, a) * Math.pow(3, b) * Math.pow(5, c)));
    public static boolean isUgly(int n) {
        if (n <= 0) {
            return false;
        }
        int[] factors = {2, 3, 5};
        //@ maintaining 0 <= i && i <= factors.length;
        //@ decreasing factors.length - i;
        for (int i = 0; i < factors.length; i++) {
            int factor = factors[i];
            //@ maintaining n > 0;
            //@ decreases n;
            while (n % factor == 0) {
                n /= factor;
            }
        }
        return n == 1;
    }
}
