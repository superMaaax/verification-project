class IsPalindrome {
    //@ requires s != null;
    //@ ensures \result <==> (\forall int k; 0 <= k && k < s.length(); s.charAt(k) == s.charAt(s.length() - 1 - k));
    public static boolean isPalindrome(String s) {
        int n = s.length();

        //@ maintaining 0 <= i && i <= s.length();
        //@ maintaining (\forall int k; 0 <= k && k < i; s.charAt(k) == s.charAt(n - 1 - k));
        //@ decreases s.length() - i;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) != s.charAt(n - 1 - i)) {
                return false;
            }
        }

        return true;
    }
}
