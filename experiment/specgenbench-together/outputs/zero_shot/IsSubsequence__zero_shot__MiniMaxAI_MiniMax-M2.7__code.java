/*@ 
  @ public class IsSubsequence {
  @   public normal_behavior
  @   requires s != null && t != null;
  @   ensures \result == true ==> s is a subsequence of t;
  @   ensures \result == false ==> s is not a subsequence of t;
  @   also public normal_behavior
  @   requires s == null || t == null;
  @   signals_only NullPointerException;
  @*/
class IsSubsequence {
    public boolean isSubsequence(String s, String t) {
        int n = s.length(), m = t.length();
        int i = 0, j = 0;

        /*@
          @ maintaining 0 <= i && i <= n && 0 <= j && j <= m;
          @ maintaining (\forall int k; 0 <= k && k < i; 
          @                 (\exists int l; k <= l && l < j; s.charAt(k) == t.charAt(l)));
          @ decreases m - j;
          @*/
        while (i < n && j < m) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == n;
    }
}
