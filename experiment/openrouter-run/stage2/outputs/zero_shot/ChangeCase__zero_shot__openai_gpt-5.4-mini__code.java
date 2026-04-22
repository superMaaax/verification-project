public class ChangeCase {

  /*@ public normal_behavior
    @ requires true;
    @ ensures (c > 'z' && \result == c)
    @      || (c >= 'a' && \result == (char)(c - 'a' + 'A'))
    @      || (c > 'Z' && c <= 'z' && \result == c)
    @      || (c >= 'A' && c <= 'Z' && \result == (char)(c - 'A' + 'a'))
    @      || ((c < 'a' && c <= 'Z') && \result == c);
    @ ensures (\result >= 'A' && \result <= 'Z') || (\result >= 'a' && \result <= 'z') || \result == c;
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
