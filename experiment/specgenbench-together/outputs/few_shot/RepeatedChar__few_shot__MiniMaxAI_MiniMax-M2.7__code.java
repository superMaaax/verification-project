import java.util.HashSet;

class RepeatedChar {
    //@ requires s != null;
    //@ ensures s.length() == 0 ==> \result == ' ';
    //@ ensures s.length() > 0 ==> (\exists int i; 0 <= i && i < s.length(); 
    //@                              (\exists int j; 0 <= j && j < s.length() && j != i; 
    //@                                  s.charAt(i) == s.charAt(j) && \result == s.charAt(i)));
    public char repeatedCharacter(String s) {
        Set<Character> seen = new HashSet<Character>();
        
        for (int i = 0; i < s.length(); i++) {
            //@ maintaining 0 <= i && i <= s.length();
            //@ maintaining (\forall int k; 0 <= k && k < i; 
            //@              (\forall int l; k < l && l < s.length(); s.charAt(k) != s.charAt(l)));
            //@ decreases s.length() - i;
            char ch = s.charAt(i);
            if (!seen.add(ch)) {
                return ch;
            }
        }
        // impossible
        return ' ';
    }
}
