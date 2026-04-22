public class NotCommonMultiple {
    /*@ public normal_behavior
      @ requires a != 0 && b != 0;
      @ ensures \result == (m % a != 0 || m % b != 0);
      @ assignable \nothing;
      @*/
    public boolean NotCommonMultiple (int a, int b, int m) {
        return m % a != 0 || m % b != 0;
    }
}
