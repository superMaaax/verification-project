class ReverseString {
    /*@ public normal_behavior
      @ requires s != null;
      @ assignable s[*];
      @ ensures (\forall int i; 0 <= i && i < s.length; s[i] == \old(s[s.length - 1 - i]));
      @*/
    public void reverseString(char[] s) {
        int n = s.length;
        /*@ loop_invariant 0 <= left && left <= n;
          @ loop_invariant -1 <= right && right < n;
          @ loop_invariant left + right == n - 1;
          @ loop_invariant (\forall int i; 0 <= i && i < left; s[i] == \old(s[n - 1 - i]));
          @ loop_invariant (\forall int i; right < i && i < n; s[i] == \old(s[n - 1 - i]));
          @ loop_invariant (\forall int i; left <= i && i <= right; true);
          @ decreases right - left;
          @*/
        for (int left = 0, right = n - 1; left < right; ++left, --right) {
            char tmp = s[left];
            s[left] = s[right];
            s[right] = tmp;
        }
    }
}
