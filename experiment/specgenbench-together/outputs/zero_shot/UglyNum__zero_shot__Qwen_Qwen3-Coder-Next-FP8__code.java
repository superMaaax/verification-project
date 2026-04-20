class UglyNum {
    /*@ spec_public @*/
    public static boolean isUgly(int n) {
        // Precondition: none explicitly required, but behavior is defined for all int values
        // Postcondition: returns true if and only if n is an ugly number (positive integer whose prime factors are only 2, 3, and 5)
        /*@ ensures (\result <==> (n > 0 && 
            @ (n % 2 == 0 || n % 3 == 0 || n % 5 == 0 || n == 1) &&
            @ \forall int p; (p > 5 && Prime(p) && p <= n) ==> (n % p != 0));
        @*/
        if (n <= 0) {
            // Postcondition for this branch: returns false
            /*@ ensures !\result; @*/
            return false;
        }
        int[] factors = {2, 3, 5};
        /*@ loop_invariant 
            @ 0 <= i && i <= factors.length &&
            @ n == \old(n) / (\prod_{int k=0; k < i; k++) factors[k]^{v_k}) 
            @   where v_k is the total exponent of factors[k] in the prime factorization of \old(n);
            @ decreases factors.length - i;
            @*/
        for (int i = 0; i < factors.length; i++) {
            int factor = factors[i];
            /*@ loop_invariant 
                @ n >= 0 &&
                @ \old(n) == n * (\prod_{int k=0; k <= i; k++) factors[k]^{e_k}) 
                @   where e_k is the number of times factors[k] divided \old(n) in this loop;
                @ decreases n / factor;
                @*/
            while (n % factor == 0) {
                n /= factor;
            }
        }
        // Postcondition for this branch: returns true if n == 1, false otherwise
        /*@ ensures (\result <==> (n == 1)); @*/
        return n == 1;
    }
}
