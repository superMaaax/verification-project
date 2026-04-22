public class Calculator {
    
    /*@ requires operator == '+' || operator == '-' || operator == '*' || 
      @                 operator == '/' || operator == '%';
      @ requires operator == '/' || operator == '%' ==> num2 != 0;
      @ ensures \result == -1 ==> operator != '+' && operator != '-' && 
      @                         operator != '*' && operator != '/' && 
      @                         operator != '%';
      @ ensures operator == '+' ==> \result == num1 + num2;
      @ ensures operator == '-' ==> \result == num1 - num2;
      @ ensures operator == '*' ==> \result == num1 * num2;
      @ ensures operator == '/' ==> \result == num1 / num2;
      @ ensures operator == '%' ==> \result == num1 % num2;
      @*/
    public int calculate(int num1, int num2, char operator) {
        int output;

        switch (operator) {
            case '+':
                output = num1 + num2;
                break;

            case '-':
                output = num1 - num2;
                break;

            case '*':
                output = num1 * num2;
                break;

            case '/':
                output = num1 / num2;
                break;

            case '%':
                output = num1 % num2;
                break;

            default:
                return -1;
        }
        return output;
    }
}
