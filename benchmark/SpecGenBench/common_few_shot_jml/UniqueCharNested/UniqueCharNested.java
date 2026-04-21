public class UniqueCharNested {

    //@ requires s != null;
    //@ ensures -1 <= \result && \result < s.length();
    //@ ensures \result >= 0 ==> (\forall int j; 0 <= j && j < s.length(); j != \result ==> s.charAt(j) != s.charAt(\result));
    public static int uniqueChar(String s) {
        //@ maintaining 0 <= i && i <= s.length();
        //@ decreases s.length() - i;
        for (int i = 0; i < s.length(); ++i) {
            int j = 0;
            //@ maintaining 0 <= j && j <= s.length();
            //@ decreases s.length() - j;
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
