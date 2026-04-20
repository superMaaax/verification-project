public class IsPrefix {

    /*@ spec_public @*/
    public boolean isPrefix (String pat, String txt) {
        // Precondition: pat and txt are non-null strings
        requires pat != null;
        requires txt != null;
        
        // Postcondition: returns true iff pat is a prefix of txt
        ensures \result <==> (pat.length() <= txt.length() && 
                             \forall int k; 0 <= k && k < pat.length(); 
                             pat.charAt(k) == txt.charAt(k));
        
        int i = 0;

        /*@ loop_invariant 0 <= i <= pat.length();
          @ loop_invariant \forall int k; 0 <= k && k < i; pat.charAt(k) == txt.charAt(k);
          @ decreases pat.length() - i;
          @*/
        while(i < pat.length()) {
            if(i >= txt.length() || pat.charAt(i) != txt.charAt(i))
                return false;
            i = i + 1;
        }

        return true;
    }

}
