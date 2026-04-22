public class MulLoop {

	/*@
	  @ requires true;
	  @ ensures (\result == a * b);
	  @*/
	public static int mulLoop(int a, int b) {
        int res = 0;
        /*@
          @ loop_invariant 0 <= i && i <= b;
          @ loop_invariant res == i * a;
          @ decreases b - i;
          @*/
        if (b >= 0) {
            for(int i = 0; i < b; i++) {
                res = res + a;
            }
        } else {
            /*@
              @ loop_invariant 0 <= i && i <= -b;
              @ loop_invariant res == -i * a;
              @ decreases (-b) - i;
              @*/
            for(int i = 0; i < -b; i++) {
                res = res - a;
            }
        }
        return res;
    }

}
