class PowerOfThree {
    /*@ spec_public @*/
    public boolean isPowerOfThree(int n) {
        /*@ 
            requires true;
            ensures (\result <==> (n > 0 && \exists int k; k >= 0 && n == (int)Math.pow(3, k)));
        @*/
        int temp = n;
        /*@ 
            loop_invariant temp > 0 && n == temp * (int)Math.pow(3, (int)(Math.log(n)/Math.log(3)) - (int)(Math.log(temp)/Math.log(3)));
            loop_invariant n % (int)Math.pow(3, (int)(Math.log(n)/Math.log(3)) - (int)(Math.log(temp)/Math.log(3))) == 0;
            decreases temp;
        @*/
        while (temp != 0 && temp % 3 == 0) {
            temp /= 3;
        }
        return temp == 1;
    }
}
