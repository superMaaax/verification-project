public class RepeatedCharNested {

    //@ requires s != null;
    //@ ensures -1 <= \result && \result < s.length();
    //@ ensures \result == -1 ==> (\forall int i; 0 <= i && i < s.length(); (\forall int j; i < j && j < s.length(); s.charAt(i) != s.charAt(j)));
    public static int repeatedChar(String s) {
        //@ maintaining 0 <= i && i <= s.length();
        //@ decreases s.length() - i;
        for (int i = 0; i < s.length(); ++i) {
            char c1 = s.charAt(i);
            //@ maintaining i < j && j <= s.length();
            //@ decreases s.length() - j;
            for (int j = i + 1; j < s.length(); ++j) {
                char c2 = s.charAt(j);
                if(c1 == c2)
                    return i;
            }
        }
        return -1;
    }
    
}