class IsPalindrome {
    /*@ spec_public @*/
    public static /*@ pure @*/ boolean isPalindrome(String s) 
        requires s != null
        ensures (\result <==> (\forall int i; 0 <= i && i < s.length()/2; s.charAt(i) == s.charAt(s.length() - 1 - i)))
    {
        int n = s.length();

        /*@ loop_invariant 0 <= i && i <= n
          @ loop_invariant (\forall int j; 0 <= j && j < i; s.charAt(j) == s.charAt(n - 1 - j))
          @ decreases n - i
          @*/
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) != s.charAt(n - 1 - i)) {
                return false;
            }
        }

        return true;
    }
}
