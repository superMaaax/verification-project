public class ChangeCase {

  /*@ 
    @ requires \true;
    @ ensures 
    @   (\result == c && (c > 'z' || (c > 'Z' && c < 'a') || c < 'A')) ||
    @   (\result == (char)(c - 'a' + 'A') && c >= 'a' && c <= 'z') ||
    @   (\result == (char)(c - 'A' + 'a') && c >= 'A' && c <= 'Z');
    @ assignable \nothing;
    @*/
  public char changeCase(char c) {
    char result = ' ';    
    if (c > 'z') {
      result = c;
    } else if (c >= 'a') {
      result =  (char)(c - 'a' + 'A');
    } else if (c > 'Z') {
      result =  c;
    } else if (c >= 'A') {
      result =  (char)(c - 'A' + 'a');
    } else {
      result = c;
    }
    return result;
  }

}
