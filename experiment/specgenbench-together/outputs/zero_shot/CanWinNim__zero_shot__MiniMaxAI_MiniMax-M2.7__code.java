/*@ 
  @ public invariant true;
  @*/
class CanWinNim {
    /*@ 
      @ requires n > 0;
      @ ensures \result == (n % 4 != 0);
      @*/
    public boolean canWinNim(int n) {
        return n % 4 != 0;
    }
}
