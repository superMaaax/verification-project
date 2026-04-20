public class NotCommonMultiple {
    /*@ spec_public @*/
    public boolean NotCommonMultiple (int a, int b, int m) {
        requires a > 0;
        requires b > 0;
        requires m >= 0;
        ensures \result <==> (m % a != 0 || m % b != 0);
    }
}
