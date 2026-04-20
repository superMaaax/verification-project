class DigitRoot {
    /*@ spec_public @*/
    public int digitRoot(int num) {
        // Precondition: num is non-negative
        // Postcondition: Returns the digital root of num (the recursive sum of its digits until a single digit is obtained)
        /*@ requires num >= 0;
            ensures \result >= 0 && \result < 10;
            ensures (\exists int i; i >= 0; 
                (\forall int j; j > 0; 
                    (j < 10 ? (\result == j) : (\result == digitRoot(\old(num) / 10) + \old(num) % 10))));
        @*/
        while (num >= 10) {
            /*@ loop_invariant num >= 10;
                loop_invariant (\exists int sum; sum >= 0; 
                    (\forall int n; n > 0; 
                        (n < 10 ? (\result == n) : (\result == digitRoot(\old(num) / 10) + \old(num) % 10))));
                decreases num;
            @*/
            int sum = 0;
            /*@ loop_invariant sum >= 0;
                loop_invariant num >= 0;
                loop_invariant (\exists int originalNum; originalNum == \old(num);
                    sum == \sum(0, \old(num), (int i) -> (i < \old(num) ? (\old(num) / (int)Math.pow(10, i) % 10) : 0)));
                decreases num;
            @*/
            while (num > 0) {
                /*@ loop_invariant sum >= 0;
                    loop_invariant num >= 0;
                    loop_invariant (\exists int tempNum; tempNum == \old(num);
                        sum == \sum(0, \old(num) - num, (int i) -> (i < \old(num) - num ? (\old(num) / (int)Math.pow(10, i) % 10) : 0)));
                    decreases num;
                @*/
                sum += num % 10;
                num /= 10;
            }
            num = sum;
        }
        return num;
    }
}
