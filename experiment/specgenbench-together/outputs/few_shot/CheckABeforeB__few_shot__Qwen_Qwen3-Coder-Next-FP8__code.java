class CheckABeforeB {
    //@ ensures \result <==> (\exists int k; 0 <= k && k <= chars.length; 
    //@   (\forall int i; 0 <= i && i < k; chars[i] == 'a') &&
    //@   (\forall int i; k <= i && i < chars.length; chars[i] == 'b'));
    public boolean checkString(String s) {
        char[] chars = s.toCharArray();
        int i = 0;
        //@ maintaining 0 <= i && i <= chars.length;
        //@ maintaining (\forall int k; 0 <= k && k < i; chars[k] == 'a');
        //@ decreases chars.length - i;
        while (i < chars.length && chars[i] == 'a') {
            i++;
        }
	int j = i;
        //@ maintaining i <= j && j <= chars.length;
        //@ maintaining (\forall int k; i <= k && k < j; chars[k] == 'b');
        //@ decreases chars.length - j;
        while (j < chars.length && chars[j] == 'b') {
            j++;
        }
        return j == chars.length;
    }
}
