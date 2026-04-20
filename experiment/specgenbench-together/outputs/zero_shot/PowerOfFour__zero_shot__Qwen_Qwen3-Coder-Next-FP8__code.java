class PowerOfFour {
    /*@ spec_public @*/
    public boolean isPowerOfFour(int n) {
        // Precondition: none explicitly required beyond the logical conditions checked
        // Postcondition: returns true if and only if n is a power of four (i.e., n = 4^k for some integer k >= 0)
        /*@ ensures (\result <==> (n > 0 && (n & (n - 1)) == 0 && (n & 0xaaaaaaaa) == 0));
          @ assignable \nothing;
          @*/
        return n > 0 && (n & (n - 1)) == 0 && (n & 0xaaaaaaaa) == 0;
    }
}
