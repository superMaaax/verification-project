public class IntSquare {

    /*@ public normal_behavior
      @ requires true;
      @ ensures \result == ((x < 0) ? (-x) * (-x) : x * x);
      @ assignable \nothing;
      @*/
    public static int squareOf(int x) {
        if(x < 0)
            x = -x;
        int res = 0;
        /*@ loop_invariant 0 <= i && i <= x;
          @ loop_invariant res == i * x;
          @ decreases x - i;
          @*/
        for(int i = 0; i < x; i++) {
            /*@ loop_invariant 0 <= j && j <= x;
              @ loop_invariant res == i * x + j;
              @ decreases x - j;
              @*/
            for(int j = 0; j < x; j++) {
                res = res + 1;
            }
        }
        return res;
    }

}
