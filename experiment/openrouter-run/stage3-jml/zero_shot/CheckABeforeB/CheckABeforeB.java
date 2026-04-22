class CheckABeforeB {
    /*@ public normal_behavior
      @ requires s != null;
      @ ensures \result <==> (\exists int i; 0 <= i && i <= s.length();
      @                       (\forall int k; 0 <= k && k < i; s.charAt(k) == 'a') &&
      @                       (\forall int k; i <= k && k < s.length(); s.charAt(k) == 'b'));
      @*/
    public boolean checkString(String s) {
        char[] chars = s.toCharArray();
        int i = 0;
        /*@
          @ loop_invariant 0 <= i && i <= chars.length;
          @ loop_invariant (\forall int k; 0 <= k && k < i; chars[k] == 'a');
          @ loop_invariant s != null;
          @ decreases chars.length - i;
          @*/
        while (i < chars.length && chars[i] == 'a') {
            i++;
        }
	int j = i;
        /*@
          @ loop_invariant i <= j && j <= chars.length;
          @ loop_invariant (\forall int k; i <= k && k < j; chars[k] == 'b');
          @ decreases chars.length - j;
          @*/
        while (j < chars.length && chars[j] == 'b') {
            j++;
        }
        return j == chars.length;
    }
}
