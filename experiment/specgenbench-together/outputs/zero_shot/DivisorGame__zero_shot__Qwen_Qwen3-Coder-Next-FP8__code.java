public class DivisorGame {
    /*@ spec_public @*/
    public boolean divisorGame(int n) {
        requires n >= 0;
        ensures \result <==> (n % 2 == 0);
        return n % 2 == 0;
    }
}
