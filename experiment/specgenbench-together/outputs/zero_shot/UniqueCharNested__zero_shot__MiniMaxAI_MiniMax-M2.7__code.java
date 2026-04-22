public class UniqueCharNested {

    /*@ requires s != null;
        ensures \result == -1 || (\result >= 0 && \result < s.length());
        ensures \result == -1 ==> 
            (\forall int k; k >= 0 && k < s.length(); 
                (\exists int l; l >= 0 && l < s.length() && l != k; 
                    s.charAt(k) == s.charAt(l)));
        ensures \result >= 0 ==> 
            (\forall int j; j >= 0 && j < s.length() && j != \result; 
                s.charAt(\result) != s.charAt(j));
    @*/
    public static int uniqueChar(String s) {
        for (int i = 0; i < s.length(); ++i) {
            int j = 0;
            /*@ maintaining j >= 0 && j <= s.length();
                maintaining (\forall int k; k >= 0 && k < j; 
                    k != i ==> s.charAt(i) != s.charAt(k));
                decreases s.length() - j;
            @*/
            while(j < s.length()) {
                if(i != j && s.charAt(i) == s.charAt(j))
                    break;
                j++;
            }
            if(j == s.length())
                return i;
        }
        return -1;
    }
    
}
