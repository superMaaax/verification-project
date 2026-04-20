public class NotCommonMultiple {
    //@ ensures \result <==> (m % a != 0 || m % b != 0);
    public boolean NotCommonMultiple (int a, int b, int m) {
        return m % a != 0 || m % b != 0;
    }
}
