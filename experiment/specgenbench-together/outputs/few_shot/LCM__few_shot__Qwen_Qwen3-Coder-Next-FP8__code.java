public class LCM {
    
    //@ requires num1 != 0 && num2 != 0;
    //@ ensures \result == -1 <==> (\forall int k; 1 <= k && k <= Integer.MAX_VALUE; !(k % num1 == 0 && k % num2 == 0));
    //@ ensures -1 < \result ==> (\exists int k1, k2; k1 >= 1 && k2 >= 1 && \result == k1 * num1 && \result == k2 * num2);
    //@ ensures -1 < \result ==> (\forall int k; 1 <= k && k < \result; !(k % num1 == 0 && k % num2 == 0));
    public static int lcm(int num1, int num2) 
    {
        if (num1 == 0 || num2 == 0) {
            return -1;
        }	
        if (num1 < 0)
            num1 = -num1;
        if (num2 < 0)
            num2 = -num2;

        int result = (num1 > num2) ? num1 : num2;

        //@ maintaining result >= (num1 > num2 ? num1 : num2);
        //@ maintaining result >= 1;
        //@ maintaining (\forall int k; (num1 > num2 ? num1 : num2) <= k && k < result; !(k % num1 == 0 && k % num2 == 0));
        //@ decreases Integer.MAX_VALUE - result;
        while (result < Integer.MAX_VALUE)
        {
            if (result % num1 == 0 && result % num2 == 0)
            {
                break;
            }
            result++;
        }

        if (result % num1 == 0 && result % num2 == 0) {
            return result;
        }
        return -1;
    }

}
