class ReverseString {
    //@ requires s != null;
    //@ ensures (\forall int i; 0 <= i && i < s.length / 2; s[i] == \old(s[s.length - 1 - i]) && s[s.length - 1 - i] == \old(s[i]));
    public void reverseString(char[] s) {
        int n = s.length;
        //@ maintaining 0 <= left && left <= right + 1 && right < n;
        //@ maintaining (\forall int k; 0 <= k && k < left; s[k] == \old(s[n - 1 - k]));
        //@ maintaining (\forall int k; right < k && k < n; s[k] == \old(s[n - 1 - k]));
        //@ decreases right - left;
        for (int left = 0, right = n - 1; left < right; ++left, --right) {
            char tmp = s[left];
            s[left] = s[right];
            s[right] = tmp;
        }
    }
}
