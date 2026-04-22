class PowerOfThree {
    /*@ spec_public @*/ private int temp;
    
    public boolean isPowerOfThree(int n) {
        int temp = n;
        //@ loop_modifies temp;
        //@ loop_invariant n == temp * (3 ^ (original_n / temp));
        while (temp != 0 && temp % 3 == 0) {
            temp /= 3;
        }
        return temp == 1;
    }
}
