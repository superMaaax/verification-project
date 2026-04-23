class IsAllUnique {
    //@ requires str != null;
    //@ ensures str.length() > 26 ==> !\result;
    boolean isAllUnique(String str) {
        int len = str.length();
        if (len > 26) {
            return false;
        }
        int num = 0;
        //@ maintaining 0 <= i && i <= len;
        //@ maintaining 0 <= num && num < (1 << 26);
        //@ decreases len - i;
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