class JewelsInStones {
    //@ ensures \result >= 0;
    //@ ensures \result <= stones.length();
    //@ ensures \result == (\sum int i; 0 <= i && i < stones.length(); 
    //@     (\exists int j; 0 <= j && j < jewels.length(); stones.charAt(i) == jewels.charAt(j)) ? 1 : 0);
    public int numJewelsInStones(String jewels, String stones) {
        int jewelsCount = 0;
        int jewelsLength = jewels.length(), stonesLength = stones.length();
        //@ maintaining 0 <= i && i <= stonesLength;
        //@ maintaining 0 <= jewelsCount && jewelsCount <= i;
        //@ maintaining jewelsCount == (\sum int k; 0 <= k && k < i; 
        //@     (\exists int j; 0 <= j && j < jewelsLength; stones.charAt(k) == jewels.charAt(j)) ? 1 : 0);
        for (int i = 0; i < stonesLength; i++) {
            char stone = stones.charAt(i);
            //@ maintaining 0 <= j && j <= jewelsLength;
            //@ maintaining (\forall int k; 0 <= k && k < j; stone != jewels.charAt(k));
            for (int j = 0; j < jewelsLength; j++) {
                char jewel = jewels.charAt(j);
                if (stone == jewel) {
                    jewelsCount++;
                    break;
                }
            }
        }
        return jewelsCount;
    }
}
