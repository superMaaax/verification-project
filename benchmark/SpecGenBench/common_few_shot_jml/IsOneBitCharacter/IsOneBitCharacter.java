class IsOneBitCharacter {
    //@ requires bits != null && bits.length >= 1;
    //@ ensures bits.length == 1 ==> \result;
    public boolean isOneBitCharacter(int[] bits) {
        int n = bits.length, i = 0;
        //@ maintaining 0 <= i && i <= n;
        //@ decreases n - i;
        while (i < n - 1) {
            i += bits[i] + 1;
        }
        return i == n - 1;
    }
}