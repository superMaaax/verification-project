class UglyNumLoop {
    //@ requires n > 0;
    //@ ensures \result <==> (\exists int k2, k3, k5; n == (2^k2) * (3^k3) * (5^k5));
    public static boolean isUgly(int n) {
        if (n <= 0) {
            return false;
        }
        int original = n;
        //@ maintaining n > 0 && n <= original && (\exists int k2, k3, k5; original == n * (2^k2) * (3^k3) * (5^k5));
        while (n % 2 == 0) {
            n /= 2;
        }
        //@ maintaining n > 0 && n <= original && (\exists int k2, k3, k5; original == n * (2^k2) * (3^k3) * (5^k5));
        while (n % 3 == 0) {
            n /= 3;
        }
        //@ maintaining n > 0 && n <= original && (\exists int k2, k3, k5; original == n * (2^k2) * (3^k3) * (5^k5));
        while (n % 5 == 0) {
            n /= 5;
        }
        return n == 1;
    }
}
