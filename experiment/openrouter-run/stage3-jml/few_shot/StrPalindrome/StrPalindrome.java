public class StrPalindrome {
   
   /*@ requires str != null;
     @ ensures \result <==> (\forall int i; 0 <= i && i < str.length() / 2; str.charAt(i) == str.charAt(str.length() - 1 - i));
     @*/
   public boolean isPalindrome(String str) {
      String reverse = "";
      
      int length = str.length();

      /*@ maintaining -1 <= i && i < length;
        @ maintaining reverse.length() == length - 1 - i;
        @ maintaining (\forall int j; 0 <= j && j < reverse.length(); reverse.charAt(j) == str.charAt(length - 1 - j));
        @ decreases i + 1;
        @*/
      for (int i = length - 1; 0 <= i; i--) {
         reverse = reverse + str.charAt(i);
	 
      }
      
      return reverse.equals(str);
   }

}
