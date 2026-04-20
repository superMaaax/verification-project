class SmallestEvenMulBranch {
    /*@ spec_public @*/
    public int smallestEvenMultiple(int n) {
        requires n > 0;
        ensures (\result % 2 == 0);
        ensures (\result == n || \result == 2 * n);
        ensures (\result >= n);
        ensures (\result == n <==> n % 2 == 0);
        
        if(n % 2 == 0) {
            return n;
        }
        return 2 * n;
    }
}
