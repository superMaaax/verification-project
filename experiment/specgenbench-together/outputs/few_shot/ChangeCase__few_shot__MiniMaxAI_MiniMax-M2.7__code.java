public class ChangeCase {

  //@ requires true;
  //@ ensures ('A' <= c && c <= 'Z') ==> \result == (char)(c - 'A' + 'a');
  //@ ensures ('a' <= c && c <= 'z') ==> \result == (char)(c - 'a' + 'A');
  //@ ensures (c < 'A' || c > 'z' || ('Z' < c && c < 'a')) ==> \result == c;
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

But we need to check the condition for other characters: The original code uses:

if (c > 'z') {
  result = c;
} else if (c >= 'a') {
  result = (char)(c - 'a' + 'A');
} else if (c > 'Z') {
  result = c;
} else if (c >= 'A') {
  result = (char)(c - 'A' + 'a');
} else {
  result = c;
}

Thus the condition for unchanged characters includes:

- c > 'z' (i.e., after 'z')
- c > 'Z' && c < 'a' (i.e., between 'Z' and 'a')
- c < 'A' (i.e., before 'A')

Thus we can combine as: (c > 'z' || (c > 'Z' && c < 'a') || c < 'A').

But note that c > 'z' includes all characters after 'z', but also includes characters after 'z' that are beyond ASCII, but also includes characters between 'z' and maybe some other characters? Actually 'z' is the last lowercase letter. So c > 'z' includes all characters after 'z', including other uppercase letters beyond 'Z'? Actually uppercase letters are before 'a' in ASCII. So c > 'z' includes characters after 'z', which includes some punctuation? Actually in ASCII, after 'z' there are '{', '|', '}', '~', and then extended ASCII. So those are unchanged.

Thus we can use condition: (c > 'z' || (c > 'Z' && c < 'a') || c < 'A').

But note that c > 'Z' && c < 'a' is equivalent to c > 'Z' && c < 'a'. However, we need to be careful: In ASCII, 'Z' < 'a' is true. So we can use that.

Thus we can write ensures: (c > 'z' || (c > 'Z' && c < 'a') || c < 'A') ==> \result == c.

But we also need to ensure that for uppercase letters, result is lowercase, and for lowercase letters, result is uppercase.

Thus we can write ensures for uppercase: ('A' <= c && c <= 'Z') ==> \result == (char)(c - 'A' + 'a').

Ensures for lowercase: ('a' <= c && c <= 'z') ==> \result == (char)(c - 'a' + 'A').

But note that the condition c >= 'a' includes lowercase letters, but also includes characters after 'a'? Actually c >= 'a' includes all characters from 'a' onward, but we only want to convert when c is between 'a' and 'z'. So we need to restrict to 'a' <= c && c <= 'z'.

Similarly for uppercase.

Thus we can write ensures accordingly.

But we also need to consider that the original code uses c >= 'a' to check for lowercase letters, but also includes characters after 'z'? Actually c >= 'a' includes all characters from 'a' onward, including '{', etc. But the code only converts when c >= 'a' and also c <= 'z'? Actually the condition is "else if (c >= 'a')" and then inside we set result = (char)(c - 'a' + 'A'). But if c is '{', then c - 'a' + 'A' would be something else, but the code would incorrectly convert it. However, the code's logic is that if c >= 'a', it assumes it's a lowercase letter. But that is not correct for characters beyond 'z'. But the code also has a check for c > 'z' before that, so if c > 'z', it returns c unchanged. So the condition c >= 'a' is only executed when c <= 'z'. So we can assume that the code only converts when c is between 'a' and 'z'. So we can specify that the conversion only occurs when c is a lowercase letter.

Thus we can write ensures that for c in 'a'..'z', result is uppercase, and for c in 'A'..'Z', result is lowercase, and otherwise unchanged.

Thus we can write ensures as:

//@ ensures ('A' <= c && c <= 'Z') ==> \result == (char)(c - 'A' + 'a');
//@ ensures ('a' <= c && c <= 'z') ==> \result == (char)(c - 'a' + 'A');
//@ ensures (c < 'A' || c > 'z' || ('Z' < c && c < 'a')) ==> \result == c;

But note that the condition ('Z' < c && c < 'a') is true for characters between 'Z' and 'a'. However, we also need to include characters between 'Z' and 'a' that are not letters. But we also need to include characters less than 'A' and greater than 'z'. So we can combine as (c < 'A' || c > 'z' || ('Z' < c && c < 'a')). But note that c > 'z' includes characters after 'z', but also includes characters after 'z' that might be uppercase? Actually uppercase letters are before 'a', so they are not > 'z'. So we can just use c > 'z' to cover those after 'z'.

But we also need to consider that characters between 'Z' and 'a' are covered by 'Z' < c && c < 'a'. However, note that c > 'z' also includes characters after 'z', but also includes characters after 'z' that might be uppercase? Actually uppercase letters are before 'a', so they are not > 'z'. So we can just use c > 'z'.

Thus we can write ensures as:

//@ ensures (c < 'A' || c > 'z' || ('Z' < c && c < 'a')) ==> \result == c;

But note that c < 'A' includes digits, punctuation, etc. c > 'z' includes characters after 'z'. 'Z' < c && c < 'a' includes characters between 'Z' and 'a'.

But we also need to consider that characters between 'Z' and 'a' are not letters, and they should remain unchanged. So we can combine.

Alternatively, we can write ensures in a simpler way: ensures \result == (char)((c >= 'a' && c <= 'z') ? (c - 'a' + 'A') : ((c >= 'A' && c <= 'Z') ? (c - 'A' + 'a') : c));

But note that the condition in the original code is: if c > 'z' then unchanged; else if c >= 'a' then convert to uppercase; else if c > 'Z' then unchanged; else if c >= 'A' then convert to lowercase; else unchanged. So the conversion only occurs for letters. So we can use that.

But we need to be careful about the condition for unchanged characters: The original code returns unchanged for c > 'z', c > 'Z' && c < 'a', and c < 'A'. So we can combine as (c > 'z' || (c > 'Z' && c < 'a') || c < 'A').

Thus we can write ensures as:

//@ ensures (c > 'z' || (c > 'Z' && c < 'a') || c < 'A') ==> \result == c;
//@ ensures (c >= 'a' && c <= 'z') ==> \result == (char)(c - 'a' + 'A');
//@ ensures (c >= 'A' && c <= 'Z') ==> \result == (char)(c - 'A' + 'a');

But note that the condition c >= 'a' && c <= 'z' is equivalent to c >= 'a' && c <= 'z'. However, the original code uses c >= 'a' to check for lowercase letters, but also includes characters beyond 'z'? Actually the code checks c > 'z' first, so when c >= 'a', it must be that c <= 'z'. So we can use that.

But we also need to consider that the condition c >= 'A' && c <= 'Z' is correct.

Thus we can write ensures accordingly.

But we also need to consider that the method returns a char, and we might want to specify that the result is a char. But JML automatically infers that.

Thus the final code will have JML comments before the method.

We also need to consider that the method is public, so we might want to add "requires true;" but it's optional. However, the problem says "Generate `requires` and `ensures` clauses for each method where appropriate." So we can add a requires clause if there are any constraints. There are none, so we can omit requires. But we might want to add a requires clause that is true to indicate that any char is allowed. But it's not necessary. The example for AddLoop includes a requires clause because of overflow. For changeCase, we can just add ensures.

Thus we will output the code with JML comments.

But we need to be careful about the formatting: The JML comments should be placed before
