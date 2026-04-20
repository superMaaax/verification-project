public class NotCommonFactor {
    /*@ spec_public @*/
    public boolean notCommonFactor (int a, int b, int factor) {
        requires factor != 0;
        ensures \result <==> (a % factor != 0 || b % factor != 0);
    }
}
