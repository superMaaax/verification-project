public class Calculator {
    
    /*@ spec_public @*/
    public  int calculate(int num1, int num2, char operator) {
        
        /*@ 
            requires operator == '+' || operator == '-' || operator == '*' || operator == '/' || operator == '%';
            ensures (\result == num1 + num2) <==> (operator == '+');
            ensures (\result == num1 - num2) <==> (operator == '-');
            ensures (\result == num1 * num2) <==> (operator == '*');
            ensures (\result == num1 / num2) <==> (operator == '/');
            ensures (\result == num1 % num2) <==> (operator == '%');
            ensures (operator != '+' && operator != '-' && operator != '*' && operator != '/' && operator != '%') <==> (\result == -1);
        @*/
        
        int output;

        switch (operator)
        {
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
            	/*@ 
                    requires num2 != 0;
                    ensures \result == num1 / num2;
                @*/
            	output = num1 / num2;
		break;

	    case '%':
		/*@ 
                    requires num2 != 0;
                    ensures \result == num1 % num2;
                @*/
		output = num1 % num2;
                break;

            default:
                return -1;
        }
        return output;
    }
}
