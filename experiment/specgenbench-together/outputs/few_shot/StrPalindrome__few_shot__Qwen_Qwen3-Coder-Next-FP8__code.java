public class StrPalindrome {
   
   //@ ensures \result <==> (\forall int i; 0 <= i && i < str.length(); str.charAt(i) == str.charAt(str.length() - 1 - i));
   public boolean isPalindrome(String str) {
      String reverse = "";
      
      int length = str.length();

      //@ maintaining 0 <= length - i && length - i <= length + 1;
      //@ maintaining reverse.length() == length - (i + 1);
      //@ maintaining (\forall int k; 0 <= k && k < reverse.length(); reverse.charAt(k) == str.charAt(length - 1 - k));
      //@ decreases i + 1;
      for (int i = length - 1; 0 <= i; i--) {
         reverse = reverse + str.charAt(i);
	 
      }
      
      return reverse.equals(str);
   }

}
