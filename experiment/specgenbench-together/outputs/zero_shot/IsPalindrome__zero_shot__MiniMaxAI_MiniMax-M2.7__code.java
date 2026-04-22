/*@ 
  @ public class IsPalindrome {
  @     public static boolean isPalindrome(String s) {
  @         requires s != null;
  @         ensures \result == true <==> 
  @             (\forall int i; 0 <= i && i < s.length(); 
  @                 s.charAt(i) == s.charAt(s.length() - 1 - i));
  @     }
  @ }
  @*/
