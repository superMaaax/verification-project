class IsSubsequence {
    //@ requires s != null && t != null;
    //@ ensures \result <==> (\forall int k; 0 <= k && k < s.length(); (\exists int l; 0 <= l && l < t.length(); true));
    public boolean isSubsequence(String s, String t) {
        int n = s.length(), m = t.length();
        int i = 0, j = 0;

        //@ maintaining 0 <= i && i <= n;
        //@ maintaining 0 <= j && j <= m;
        //@ maintaining i <= j;
        //@ decreases (n - i) + (m - j);
        while (i < n && j < m) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == n;
    }
}
