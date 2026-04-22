public class RepeatedCharNested {

    /*@ public normal_behavior
      @ requires s != null;
      @ ensures (\result == -1 && (\forall int i, j; 0 <= i && i < j && j < s.length();
      @                              s.charAt(i) != s.charAt(j)))
      @      || (\result >= 0 && \result < s.length()
      @          && (\exists int j; \result < j && j < s.length();
      @                 s.charAt(\result) == s.charAt(j)));
      @ assignable \nothing;
      @*/
    public static int repeatedChar(String s) {
        /*@ loop_invariant 0 <= i && i <= s.length();
          @ loop_invariant (\forall int p, q; 0 <= p && p < q && q < i;
          @                    s.charAt(p) != s.charAt(q));
          @ decreases s.length() - i;
          @*/
        for (int i = 0; i < s.length(); ++i) {
            char c1 = s.charAt(i);
            /*@ loop_invariant i + 1 <= j && j <= s.length();
              @ loop_invariant (\forall int k; i + 1 <= k && k < j;
              @                    c1 != s.charAt(k));
              @ decreases s.length() - j;
              @*/
            for (int j = i + 1; j < s.length(); ++j) {
                char c2 = s.charAt(j);
                if(c1 == c2)
                    return i;
            }
        }
        return -1;
    }
    
}
