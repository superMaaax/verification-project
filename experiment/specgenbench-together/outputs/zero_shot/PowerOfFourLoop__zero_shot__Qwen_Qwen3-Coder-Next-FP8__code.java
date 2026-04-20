class PowerOfFourLoop {
    /*@ spec_public @*/
    public boolean isPowerOfFour(int n) {
        // Precondition: none explicitly required beyond what's handled in method
        
        // Postcondition: returns true if and only if n is a power of four
        // (i.e., n = 4^k for some integer k >= 0)
        /*@ ensures \result <==> (\exists int k; k >= 0 && n == (int)Math.pow(4, k));
            @ also
            @ ensures \result <==> (n > 0 && \forall int i; 1 <= i && i < n; (n % i == 0 && i % 4 == 0) ==> (n / i) % 4 == 0) && (n == 1 || (n % 4 == 0 && \exists int m; m == n/4 && isPowerOfFour(m))));
            @*/
        
        if(n <= 0) {
            return false;
        }
        
        /*@ loop_invariant n > 0 && (\exists int k; k >= 0 && original_n == n * (int)Math.pow(4, k));
            @ loop_invariant n % 4 != 0 || n == 1;
            @ decreases n;
            @*/
        while (n % 4 == 0) {
            n /= 4;
        }
        
        return n == 1;
    }
}
