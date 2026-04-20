class FizzBuzz {
    /*@ spec_public @*/
    public int fizzBuzz(int n) {
        /*@ 
            requires n >= 0;
            ensures (\result == 0) <==> (n % 3 != 0 && n % 5 != 0);
            ensures (\result == 3) <==> (n % 3 == 0 && n % 5 != 0);
            ensures (\result == 5) <==> (n % 3 != 0 && n % 5 == 0);
            ensures (\result == 8) <==> (n % 3 == 0 && n % 5 == 0);
        @*/
        int res = 0;
        /*@ 
            loop_invariant res == 0 || res == 3;
            loop_invariant (n % 3 == 0) ==> (res == 3);
            loop_invariant (n % 3 != 0) ==> (res == 0);
            decreases 0;
        @*/
        if (n % 3 == 0) {
            res += 3;
        }
        /*@ 
            loop_invariant res == 0 || res == 3 || res == 5 || res == 8;
            loop_invariant (n % 5 == 0) ==> (res >= 5);
            loop_invariant (n % 5 != 0) ==> (res == res\pre);
            decreases 0;
        @*/
        if (n % 5 == 0) {
            res += 5;
        }
        return res;
    }
}
