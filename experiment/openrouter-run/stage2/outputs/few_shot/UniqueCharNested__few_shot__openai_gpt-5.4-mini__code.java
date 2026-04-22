public class UniqueCharNested {

    //@ requires s != null;
    //@ ensures (\result == -1) <==> (\forall int i; 0 <= i && i < s.length(); (\exists int j; 0 <= j && j < s.length() && i != j; s.charAt(i) == s.charAt(j)));
    //@ ensures \result != -1 ==> 0 <= \result && \result < s.length();
    //@ ensures \result != -1 ==> (\forall int j; 0 <= j && j < s.length() && j != \result; s.charAt(\result) != s.charAt(j));
    public static int uniqueChar(String s) {
        //@ maintaining 0 <= i && i <= s.length();
        //@ maintaining (\forall int k; 0 <= k && k < i; (\exists int m; 0 <= m && m < s.length() && k != m; s.charAt(k) == s.charAt(m)));
        //@ decreases s.length() - i;
        for (int i = 0; i < s.length(); ++i) {
            int j = 0;
            //@ maintaining 0 <= j && j <= s.length();
            //@ maintaining (\forall int k; 0 <= k && k < j; i == k || s.charAt(i) != s.charAt(k));
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
