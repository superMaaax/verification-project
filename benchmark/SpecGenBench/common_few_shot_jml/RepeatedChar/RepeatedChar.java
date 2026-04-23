import java.util.HashSet;

class RepeatedChar {
    //@ requires s != null;
    //@ ensures (\result != ' ') ==> (\exists int i; 0 <= i && i < s.length(); s.charAt(i) == \result);
    public char repeatedCharacter(String s) {
        Set<Character> seen = new HashSet<Character>();
        
        //@ maintaining 0 <= i && i <= s.length();
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