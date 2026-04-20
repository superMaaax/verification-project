class IsOneBitCharacter {
    //@ requires bits != null;
    //@ ensures \result <==> (i == n - 1) where i and n are the final values after execution;
    //@ ensures \result <==> (\forall int j; 0 <= j < n-1; (bits[j] == 0 && j == n-2) || (bits[j] == 1 && j < n-2 && bits[j+1] == 0) || (bits[j] == 1 && j < n-2 && bits[j+1] == 1)) ==>
    //@   (\exists int k; 0 <= k < n-1; (k == 0 || bits[k-1] == 1) && (k == n-2 || bits[k] == 0)) && 
    //@   (\forall int m; 0 <= m < n-1; (m == 0 || bits[m-1] == 1) && (m == n-2 || bits[m] == 0) ==> m == k));
    public boolean isOneBitCharacter(int[] bits) {
        int n = bits.length, i = 0;
        //@ maintaining 0 <= i && i <= n;
        //@ maintaining (\forall int j; 0 <= j < i; (j == 0 || bits[j-1] == 1) && (j < n-1 ==> (bits[j] == 0 || bits[j] == 1)));
        //@ decreases n - 1 - i;
        while (i < n - 1) {
            i += bits[i] + 1;
        }
        return i == n - 1;
    }
}
