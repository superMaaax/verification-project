public class ChangeCase {

  //@ ensures (c > 'z' || (c > 'Z' && c < 'a') || c < 'A') ==> \result == c;
  //@ ensures ('a' <= c && c <= 'z') ==> \result == (char)(c - 'a' + 'A');
  //@ ensures ('A' <= c && c <= 'Z') ==> \result == (char)(c - 'A' + 'a');
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
