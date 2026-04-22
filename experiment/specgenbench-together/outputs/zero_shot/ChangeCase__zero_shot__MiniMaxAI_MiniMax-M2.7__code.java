public class ChangeCase {

  /*@
    @ requires true;
    @ ensures \result == c || 
    @         (\result >= 'A' && \result <= 'Z' && c >= 'a' && c <= 'z') ||
    @         (\result >= 'a' && \result <= 'z' && c >= 'A' && c <= 'Z');
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
