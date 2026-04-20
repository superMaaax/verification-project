public class StrPalindrome {
   
   /*@ spec_public @*/
   public boolean isPalindrome(String str) {
      /*@ requires str != null;
        @ ensures \result <==> (\forall int i; 0 <= i && i < str.length(); 
        @                          str.charAt(i) == str.charAt(str.length() - 1 - i));
        @*/
      String reverse = "";
      
      int length = str.length();

      /*@ loop_invariant 0 <= i && i <= length;
        @ loop_invariant reverse.length() == length - 1 - i;
        @ loop_invariant (\forall int j; 0 <= j && j < reverse.length(); 
        @                  reverse.charAt(j) == str.charAt(length - 1 - j));
        @ decreases i + 1;
        @*/
      for (int i = length - 1; 0 <= i; i--) {
         reverse = reverse + str.charAt(i);
	 
      }
      
      return reverse.equals(str);
   }

}
