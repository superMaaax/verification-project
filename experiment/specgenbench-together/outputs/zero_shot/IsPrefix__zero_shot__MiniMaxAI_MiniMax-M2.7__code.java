public class IsPrefix {

    /*@ requires pat != null;
        requires txt != null;
        ensures \result == (pat.length() <= txt.length() && (\forall int k; 0 <= k < pat.length(); pat.charAt(k) == txt.charAt(k)));
    @*/
    public boolean isPrefix (String pat, String txt) {
        int i = 0;

        /*@ maintaining i >= 0 && i <= pat.length() && (\forall int k; 0 <= k < i; pat.charAt(k) == txt.charAt(k));
            decreases pat.length() - i;
        @*/
        while(i < pat.length()) {
            if(i >= txt.length() || pat.charAt(i) != txt.charAt(i))
                return false;
            i = i + 1;
        }

        return true;
    }

}
