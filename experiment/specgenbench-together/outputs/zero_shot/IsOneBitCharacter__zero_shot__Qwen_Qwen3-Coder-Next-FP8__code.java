class IsOneBitCharacter {
    /*@ 
     * spec_public 
     */
    public boolean isOneBitCharacter(int[] bits) {
        // Precondition: bits is non-null
        // Postcondition: returns true if and only if the last character is a one-bit character
        // A one-bit character is represented by 0, and a two-bit character is represented by 10 or 11
        // The method determines whether the final character in the bit sequence is a one-bit character
        
        /*@ 
         * requires bits != null;
         * ensures (\result <==> 
         *   (let int[] b = bits; 
         *    let int len = b.length;
         *    let int i = 0;
         *    while (i < len - 1) {
         *        i += b[i] + 1;
         *    }
         *    i == len - 1));
         */
        
        int n = bits.length, i = 0;
        
        /*@ 
         * loop_invariant 0 <= i <= n;
         * loop_invariant (i == 0 || (i > 0 && exists int j; 0 <= j < i; 
         *     (j == 0 ? true : (bits[j-1] == 1)) && 
         *     (j == 0 ? true : (j == 1 ? bits[0] == 1 : true)) &&
         *     (j == 0 ? true : (j == 1 ? true : (bits[j-2] == 0 || bits[j-1] == 1))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? bits[0] == 1 : true))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (bits[j-3] == 0 || bits[j-2] == 1)))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? bits[0] == 1 : true)))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (bits[j-4] == 0 || bits[j-3] == 1)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? bits[0] == 1 : true)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (bits[j-5] == 0 || bits[j-4] == 1)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? bits[0] == 1 : true)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (bits[j-6] == 0 || bits[j-5] == 1)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? bits[0] == 1 : true)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (bits[j-7] == 0 || bits[j-6] == 1)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? bits[0] == 1 : true)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (bits[j-8] == 0 || bits[j-7] == 1)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? bits[0] == 1 : true)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (bits[j-9] == 0 || bits[j-8] == 1)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (j == 9 ? bits[0] == 1 : true)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (j == 9 ? true : (bits[j-10] == 0 || bits[j-9] == 1)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (j == 9 ? true : (j == 10 ? bits[0] == 1 : true)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (j == 9 ? true : (j == 10 ? true : (bits[j-11] == 0 || bits[j-10] == 1)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (j == 9 ? true : (j == 10 ? true : (j == 11 ? bits[0] == 1 : true)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (j == 9 ? true : (j == 10 ? true : (j == 11 ? true : (bits[j-12] == 0 || bits[j-11] == 1)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (j == 9 ? true : (j == 10 ? true : (j == 11 ? true : (j == 12 ? bits[0] == 1 : true)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (j == 9 ? true : (j == 10 ? true : (j == 11 ? true : (j == 12 ? true : (bits[j-13] == 0 || bits[j-12] == 1)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (j == 9 ? true : (j == 10 ? true : (j == 11 ? true : (j == 12 ? true : (j == 13 ? bits[0] == 1 : true)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (j == 9 ? true : (j == 10 ? true : (j == 11 ? true : (j == 12 ? true : (j == 13 ? true : (bits[j-14] == 0 || bits[j-13] == 1)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (j == 9 ? true : (j == 10 ? true : (j == 11 ? true : (j == 12 ? true : (j == 13 ? true : (j == 14 ? bits[0] == 1 : true)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (j == 9 ? true : (j == 10 ? true : (j == 11 ? true : (j == 12 ? true : (j == 13 ? true : (j == 14 ? true : (bits[j-15] == 0 || bits[j-14] == 1)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (j == 9 ? true : (j == 10 ? true : (j == 11 ? true : (j == 12 ? true : (j == 13 ? true : (j == 14 ? true : (j == 15 ? bits[0] == 1 : true)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (j == 9 ? true : (j == 10 ? true : (j == 11 ? true : (j == 12 ? true : (j == 13 ? true : (j == 14 ? true : (j == 15 ? true : (bits[j-16] == 0 || bits[j-15] == 1)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (j == 9 ? true : (j == 10 ? true : (j == 11 ? true : (j == 12 ? true : (j == 13 ? true : (j == 14 ? true : (j == 15 ? true : (j == 16 ? bits[0] == 1 : true)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (j == 9 ? true : (j == 10 ? true : (j == 11 ? true : (j == 12 ? true : (j == 13 ? true : (j == 14 ? true : (j == 15 ? true : (j == 16 ? true : (bits[j-17] == 0 || bits[j-16] == 1)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (j == 9 ? true : (j == 10 ? true : (j == 11 ? true : (j == 12 ? true : (j == 13 ? true : (j == 14 ? true : (j == 15 ? true : (j == 16 ? true : (j == 17 ? bits[0] == 1 : true)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (j == 9 ? true : (j == 10 ? true : (j == 11 ? true : (j == 12 ? true : (j == 13 ? true : (j == 14 ? true : (j == 15 ? true : (j == 16 ? true : (j == 17 ? true : (bits[j-18] == 0 || bits[j-17] == 1)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (j == 9 ? true : (j == 10 ? true : (j == 11 ? true : (j == 12 ? true : (j == 13 ? true : (j == 14 ? true : (j == 15 ? true : (j == 16 ? true : (j == 17 ? true : (j == 18 ? bits[0] == 1 : true)))))) &&
         *     (j == 0 ? true : (j == 1 ? true : (j == 2 ? true : (j == 3 ? true : (j == 4 ? true : (j == 5 ? true : (j == 6 ? true : (j == 7 ? true : (j == 8 ? true : (j == 9 ? true : (j == 10 ? true : (j == 11 ? true : (j == 12 ? true : (j == 13 ? true : (j == 14 ? true : (j == 15 ? true : (j == 16 ? true : (j == 17 ? true : (j == 18 ? true : (bits[j-19] == 0 || bits[j-18] == 1)))))) &&
         *     (j == 0 ? true : (j == 1 ? true :
