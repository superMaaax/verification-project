class JewelsInStones {
    /*@
      @ requires jewels != null;
      @ requires stones != null;
      @ ensures \result >= 0;
      @ ensures \result <= stones.length();
      @*/
    public int numJewelsInStones(String jewels, String stones) {
        int jewelsCount = 0;
        int jewelsLength = jewels.length(), stonesLength = stones.length();
        /*@
          @ maintaining 0 <= i && i <= stonesLength;
          @ maintaining 0 <= jewelsCount && jewelsCount <= i;
          @ maintaining jewelsLength == jewels.length();
          @ maintaining stonesLength == stones.length();
          @ decreasing stonesLength - i;
          @*/
        for (int i = 0; i < stonesLength; i++) {
            char stone = stones.charAt(i);
            /*@
              @ maintaining 0 <= j && j <= jewelsLength;
              @ maintaining jewelsLength == jewels.length();
              @ maintaining stonesLength == stones.length();
              @ decreasing jewelsLength - j;
              @*/
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
