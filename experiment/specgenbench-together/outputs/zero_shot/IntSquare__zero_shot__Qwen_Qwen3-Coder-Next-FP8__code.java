public class IntSquare {

    /*@ spec_public @*/
    public static int squareOf(int x) {
        requires x >= 0;
        ensures \result == x * x;
        
        if(x < 0)
            x = -x;
        int res = 0;
        /*@ loop_invariant 0 <= i && i <= x && res == i * x;
            loop_decreases x - i;
        @*/
        for(int i = 0; i < x; i++) {
            /*@ loop_invariant 0 <= j && j <= x && res == i * x + j;
                loop_decreases x - j;
            @*/
            for(int j = 0; j < x; j++) {
                res = res + 1;
            }
        }
        ensures \result == x * x;
        return res;
    }

}
