class IsAllUnique {
    /*@ spec_public @*/
    boolean isAllUnique(String str) {
        int len = str.length();
        // Precondition: str is not null (implicitly assumed by Java String.length() and charAt())
        /*@ requires str != null;
            ensures \result <==> (\forall int i, j; 0 <= i < len && 0 <= j < len && i != j; str.charAt(i) != str.charAt(j));
        @*/
        if (len > 26) {
            /*@ ensures !\result;
                ensures \result <==> false;
            @*/
            return false;
        }
        int num = 0;
        /*@ loop_invariant 0 <= i <= len;
            loop_invariant (\forall int k; 0 <= k < i; 
                (\exists int bit; bit == str.charAt(k) - 'a' && 0 <= bit < 26 && (num & (1 << bit)) != 0));
            loop_invariant (\forall int k, l; 0 <= k < i && 0 <= l < i && k != l; 
                str.charAt(k) != str.charAt(l));
            loop_invariant num >= 0 && num < (1 << 26);
            decreases len - i;
        @*/
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            int index = c - 'a';
            /*@ requires 0 <= index < 26;
                ensures (num & (1 << index)) != 0 <==> (\exists int k; 0 <= k < i; str.charAt(k) == c);
            @*/
            if ((num & (1 << index)) != 0) {
                /*@ ensures !\result;
                    ensures \result <==> false;
                @*/
                return false;
            } else {
                num |= (1 << index);
            }
        }
        /*@ ensures \result;
            ensures \result <==> true;
        @*/
        return true;
    }
}
