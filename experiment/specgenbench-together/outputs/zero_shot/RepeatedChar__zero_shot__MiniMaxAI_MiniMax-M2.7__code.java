import java.util.HashSet;

//@ public model nonnull
class RepeatedChar {
    //@ public normal_behavior
    //@   requires s != null;
    //@   ensures \result == ' ' ==> s.length() < 2;
    //@   ensures \result != ' ' ==> (\exists int i; 0 <= i < s.length(); 
    //@                                (\exists int j; 0 <= j < i; s.charAt(i) == s.charAt(j)));
    public char repeatedCharacter(String s) {
        //@ public model Set<Character> seen;
        Set<Character> seen = new HashSet<Character>();
        
        //@ loop_modifies seen;
        //@ loop_invariant 0 <= i && i <= s.length();
        //@ loop_invariant (\forall int k; 0 <= k < i; seen.contains(s.charAt(k)));
        //@ loop_invariant (\forall char c; seen.contains(c) ==> 
        //@                  (\exists int k; 0 <= k < i; s.charAt(k) == c));
        //@ decreases s.length() - i;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!seen.add(ch)) {
                return ch;
            }
        }
        // impossible
        return ' ';
    }
}
