public class IsSuffix {

    //@ requires pat != null && txt != null && pat.length() <= txt.length();
    //@ ensures pat.length() == 0 ==> \result;
    //@ ensures \result ==> (\forall int k; 0 <= k && k < pat.length(); pat.charAt(k) == txt.charAt(k - pat.length() + txt.length()));
    public boolean isSuffix (String pat, String txt) {
	
        int i = pat.length() - 1;

        //@ maintaining -1 <= i && i <= pat.length() - 1;
        //@ maintaining (\forall int k; i < k && k < pat.length(); pat.charAt(k) == txt.charAt(k - pat.length() + txt.length()));
        //@ decreases i + 1;
        while(i >= 0) {
	    int j = i - pat.length() + txt.length();
            if(j < 0 || pat.charAt(i) != txt.charAt(j))
                return false;
            i = i - 1;
        }

        return true;
    }

}
