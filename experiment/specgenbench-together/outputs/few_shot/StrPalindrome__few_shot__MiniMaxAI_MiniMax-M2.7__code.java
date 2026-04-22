public class StrPalindrome {
   
   //@ requires str != null;
   //@ ensures \result == true <==> (\forall int i; 0 <= i && i < str.length(); str.charAt(i) == str.charAt(str.length() - 1 - i));
   public boolean isPalindrome(String str) {
      String reverse = "";
      
      int length = str.length();

      for (int i = length - 1; 0 <= i; i--) {
         reverse = reverse + str.charAt(i);
         //@ maintaining 0 <= i && i < length;
         //@ maintaining reverse.equals(new StringBuilder(str.substring(i+1)).reverse().toString());
         //@ decreases i;
      }
      
      return reverse.equals(str);
   }

}
