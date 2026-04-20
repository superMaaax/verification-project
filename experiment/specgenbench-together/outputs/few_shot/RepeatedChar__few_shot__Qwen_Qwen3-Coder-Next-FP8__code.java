import java.util.HashSet;

class RepeatedChar {
    //@ requires s != null;
    //@ ensures (\exists int i; 0 <= i && i < s.length(); 
    //@   (\forall int j; 0 <= j && j < i; s.charAt(j) != s.charAt(i)) && 
    //@   (\exists int k; 0 <= k && k < i; s.charAt(k) == s.charAt(i))) 
    //@   ==> \result == s.charAt(i);
    //@ ensures (\forall int i; 0 <= i && i < s.length(); 
    //@   (\forall int j; 0 <= j && j < i; s.charAt(j) != s.charAt(i))) 
    //@   ==> \result == ' ';
    public char repeatedCharacter(String s) {
        Set<Character> seen = new HashSet<Character>();
        
        //@ maintaining 0 <= i && i <= s.length();
        //@ maintaining (\forall char c; seen.contains(c); 
        //@   (\exists int j; 0 <= j && j < i; s.charAt(j) == c));
        //@ maintaining (\forall int j; 0 <= j && j < i; 
        //@   (\forall int k; 0 <= k && k < j; s.charAt(j) != s.charAt(k)) <==> !seen.contains(s.charAt(j)));
        //@ decreases s.length() - i;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!seen.add(ch)) {
                //@ assert (\exists int j; 0 <= j && j < i; s.charAt(j) == ch);
                return ch;
            }
        }
        // impossible
        return ' ';
    }
}
