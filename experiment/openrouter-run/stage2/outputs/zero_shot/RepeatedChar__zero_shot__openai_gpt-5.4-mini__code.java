class RepeatedChar {
    /*@
      @ requires s != null;
      @ ensures (\exists int i; 0 <= i && i < s.length();
      @             \result == s.charAt(i) &&
      @             (\exists int j; 0 <= j && j < i; s.charAt(j) == \result));
      @*/
    public char repeatedCharacter(String s) {
        Set<Character> seen = new HashSet<Character>();
        
        /*@
          @ loop_invariant 0 <= i && i <= s.length();
          @ loop_invariant (\forall int k; 0 <= k && k < i;
          @                     seen.contains(s.charAt(k)));
          @ loop_invariant (\forall int k, j; 0 <= k && k < j && j < i;
          @                     s.charAt(k) != s.charAt(j));
          @ decreases s.length() - i;
          @*/
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
