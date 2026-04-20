public class ChangeCase {

  //@ ensures (\exists char lower = 'a', upper = 'A'; 
  //@   (c >= lower && c <= 'z') ==>
  //@     (\result == (c >= lower && c <= 'z' ? (char)(c - 'a' + 'A') : 
  //@                  (c >= upper && c <= 'Z' ? (char)(c - 'A' + 'a') : c)));
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
