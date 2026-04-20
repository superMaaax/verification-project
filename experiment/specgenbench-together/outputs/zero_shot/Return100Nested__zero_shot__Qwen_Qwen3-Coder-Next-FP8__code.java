public class Return100Nested {
    /*@ spec_public @*/
    public static int return100 () {
        int res = 0;
        /*@ loop_invariant 0 <= i && i <= 10;
            loop_invariant res == (10 * i);
            decreases 10 - i;
        @*/
        for(int i = 0; i < 10; i++) {
            /*@ loop_invariant 0 <= j && j <= 10;
                loop_invariant res == (10 * i) + j;
                decreases 10 - j;
            @*/
            for(int j = 0; j < 10; j++) {
                res = res + 1;
            }
        }
        /*@ ensures \result == 100; @*/
        return res;
    }
}
