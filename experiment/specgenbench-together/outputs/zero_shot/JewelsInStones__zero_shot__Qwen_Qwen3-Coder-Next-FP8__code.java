class JewelsInStones {
    /*@ spec_public @*/
    public int numJewelsInStones(String jewels, String stones) {
        // Precondition: jewels and stones are not null
        requires jewels != null;
        requires stones != null;
        
        // Postcondition: result is the count of characters in stones that appear in jewels
        ensures \result >= 0;
        ensures \result <= stones.length();
        ensures \result <= jewels.length() * stones.length();
        
        int jewelsCount = 0;
        int jewelsLength = jewels.length(), stonesLength = stones.length();
        
        // Outer loop specification
        /*@ loop_invariant 0 <= i && i <= stonesLength;
            loop_invariant 0 <= jewelsCount && jewelsCount <= i * jewelsLength;
            decreases stonesLength - i;
        @*/
        for (int i = 0; i < stonesLength; i++) {
            char stone = stones.charAt(i);
            
            // Inner loop specification
            /*@ loop_invariant 0 <= j && j <= jewelsLength;
                loop_invariant (\forall int k; 0 <= k && k < j; stone != jewels.charAt(k));
                decreases jewelsLength - j;
            @*/
            for (int j = 0; j < jewelsLength; j++) {
                char jewel = jewels.charAt(j);
                if (stone == jewel) {
                    jewelsCount++;
                    break;
                }
            }
        }
        
        // Postcondition: result equals the count of stones that are jewels
        ensures \result == (\sum int k; 0 <= k && k < stonesLength; 
            (\exists int l; 0 <= l && l < jewelsLength; stones.charAt(k) == jewels.charAt(l)) ? 1 : 0);
        
        return jewelsCount;
    }
}
