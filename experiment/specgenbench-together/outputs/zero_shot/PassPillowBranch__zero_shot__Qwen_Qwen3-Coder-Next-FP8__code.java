class PassPillowBranch {
    /*@ 
     * @ requires n >= 2;
     * @ requires time >= 0;
     * @ ensures result >= 1 && result <= n;
     * @ ensures (\exists int k; 0 <= k && k < n; result == k + 1) || 
     *         (\exists int k; 0 <= k && k < n; result == 2*n - k - 1);
     * @ normal_behavior
     */
    public int passPillow(int n, int time) {
        /*@ 
         * @ loop_invariant 0 <= time && time <= (n-1)*2;
         * @ loop_invariant (\exists int k; 0 <= k && k < n; time == k*(n-1)*2 + original_time % ((n-1)*2));
         * @ decreases (n-1)*2 - (time % ((n-1)*2));
         */
        time = time % (n - 1) * 2;
        /*@ 
         * @ ensures (time < n) ==> (\result == time + 1);
         * @ ensures (time >= n) ==> (\result == n * 2 - time - 1);
         */
        if (time < n) {
            /*@ 
             * @ ensures \result == time + 1;
             * @ ensures \result >= 1 && \result <= n;
             */
            return time + 1;
        }
        /*@ 
         * @ ensures \result == n * 2 - time - 1;
         * @ ensures \result >= 1 && \result <= n;
         */
        return n * 2 - time - 1;
    }
}
