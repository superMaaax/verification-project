class IsSubsequence {
    //@ requires s != null && t != null;
    //@ ensures s.length() == 0 ==> \result;
    public boolean isSubsequence(String s, String t) {
        int n = s.length(), m = t.length();
        int i = 0, j = 0;

        //@ maintaining 0 <= i && i <= n;
        //@ maintaining 0 <= j && j <= m;
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
