public class RepeatedCharNested {

    //@ requires s != null;
    //@ ensures (\result == -1) <==> (\forall int i, j; 0 <= i && i < j && j < s.length(); s.charAt(i) != s.charAt(j));
    //@ ensures \result != -1 ==> 0 <= \result && \result < s.length();
    //@ ensures \result != -1 ==> (\exists int j; \result < j && j < s.length(); s.charAt(\result) == s.charAt(j));
    public static int repeatedChar(String s) {
        //@ maintaining 0 <= i && i <= s.length();
        //@ maintaining (\forall int p, q; 0 <= p && p < q && q < i; s.charAt(p) != s.charAt(q));
        //@ decreases s.length() - i;
        for (int i = 0; i < s.length(); ++i) {
            char c1 = s.charAt(i);
            //@ maintaining i + 1 <= j && j <= s.length();
            //@ maintaining (\forall int k; i < k && k < j; s.charAt(i) != s.charAt(k));
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
