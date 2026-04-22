class IsAllUnique {
    /*@
      @ requires str != null;
      @ ensures \result <==> (\forall int i, j; 0 <= i && i < j && j < str.length();
      @                        str.charAt(i) != str.charAt(j));
      @*/
    boolean isAllUnique(String str) {
        int len = str.length();
        if (len > 26) {
            return false;
        }
        int num = 0;
        /*@
          @ loop invariant 0 <= i && i <= len;
          @ loop invariant num >= 0;
          @ loop invariant (\forall int p, q; 0 <= p && p < q && q < i;
          @                  str.charAt(p) != str.charAt(q));
          @ decreases len - i;
          @*/
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            int index = c - 'a';
            if ((num & (1 << index)) != 0) {
                return false;
            } else {
                num |= (1 << index);
            }
        }
        return true;
    }
}
