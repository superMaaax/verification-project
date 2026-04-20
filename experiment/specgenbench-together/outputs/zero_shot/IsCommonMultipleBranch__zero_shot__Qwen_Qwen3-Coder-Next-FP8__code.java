public class IsCommonMultipleBranch {
    /*@ spec_public @*/
    public boolean isCommonMultiple (int a, int b, int m) {
        // Precondition: a, b, and m are integers
        // Postcondition: returns true if and only if m is divisible by both a and b
        /*@ requires a != 0 || m == 0;
            requires b != 0 || m == 0;
            ensures (\result <==> (m % a == 0 && m % b == 0));
        @*/
        if(m % a != 0) {
            return false;
        }
        if(m % b != 0) {
            return false;
        }
        return true;
    }
}
