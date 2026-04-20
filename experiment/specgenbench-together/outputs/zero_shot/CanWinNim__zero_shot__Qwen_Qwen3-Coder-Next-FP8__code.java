class CanWinNim {
    /*@ 
     * @spec_public
     */
    public /*@ pure @*/ boolean canWinNim(int n) 
        /*@ 
            requires n >= 0;
            ensures (\result <==> (n % 4 != 0));
         @*/
    {
        return n % 4 != 0;
    }
}
