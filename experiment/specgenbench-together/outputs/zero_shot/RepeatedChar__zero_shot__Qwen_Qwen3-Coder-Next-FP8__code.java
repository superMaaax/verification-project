import java.util.HashSet;

class RepeatedChar {
    /*@ spec_public @*/
    public char repeatedCharacter(String s) {
        /*@ requires s != null;
            ensures (\forall int i; 0 <= i && i < \result_index;
                (\forall int j; 0 <= j && j < i; s.charAt(j) != s.charAt(i)) &&
                (\exists int j; 0 <= j && j < i; s.charAt(j) == s.charAt(i)) ==>
                \result == s.charAt(i)) &&
                (\forall int i; 0 <= i && i < s.length(); 
                    (\exists int j; 0 <= j && j < i; s.charAt(j) == s.charAt(i)) ==>
                    \result == s.charAt(i));
            ensures (\forall int i; 0 <= i && i < s.length(); 
                (\forall int j; 0 <= j && j < i; s.charAt(j) != s.charAt(i)) ==>
                \result == ' ');
            assignable \nothing;
        @*/
        Set<Character> seen = new HashSet<Character>();
        
        /*@ loop_invariant 
            (\forall int j; 0 <= j && j < i; 
                seen.contains(s.charAt(j))) &&
            (\forall char c; 
                (\exists int j; 0 <= j && j < i; s.charAt(j) == c) ==>
                seen.contains(c)) &&
            (\forall char c; 
                seen.contains(c) ==>
                (\exists int j; 0 <= j && j < i; s.charAt(j) == c));
            loop_invariant 0 <= i && i <= s.length();
            decreases s.length() - i;
        @*/
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            /*@ assert ch != '\u0000' || s.length() > 0; @*/
            if (!seen.add(ch)) {
                /*@ assert (\exists int j; 0 <= j && j < i; s.charAt(j) == ch); @*/
                return ch;
            }
        }
        // impossible
        return ' ';
    }
}
