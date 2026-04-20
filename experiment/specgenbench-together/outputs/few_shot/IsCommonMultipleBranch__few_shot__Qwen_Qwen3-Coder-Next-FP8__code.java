public class IsCommonMultipleBranch {
    //@ ensures \result <==> (m % a == 0 && m % b == 0);
    public boolean isCommonMultiple (int a, int b, int m) {
        if(m % a != 0) {
            return false;
        }
        if(m % b != 0) {
            return false;
        }
        return true;
    }
}
