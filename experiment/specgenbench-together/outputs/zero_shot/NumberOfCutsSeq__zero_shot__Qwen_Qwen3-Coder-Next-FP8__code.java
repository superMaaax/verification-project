class NumberOfCutsSeq {
    /*@ spec_public @*/
    public int numberOfCuts(int n) {
        // Precondition: n must be non-negative
        // Postcondition: Returns 0 if n == 1, n/2 if n is even and n > 1, and n if n is odd and n > 1
        /*@ requires n >= 0;
            ensures (n == 1) <==> (\result == 0);
            ensures (n != 1 && n % 2 == 0) <==> (\result == n / 2);
            ensures (n != 1 && n % 2 != 0) <==> (\result == n);
        @*/
        return ((n == 1) ? 0 : ((n % 2 == 0) ? (n / 2) : n));
    }
}
