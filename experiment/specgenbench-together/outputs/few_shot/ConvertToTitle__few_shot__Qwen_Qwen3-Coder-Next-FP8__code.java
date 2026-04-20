class ConvertToTitle {
    //@ requires columnNumber > 0;
    //@ ensures \result != null;
    //@ ensures \result.length() > 0;
    //@ ensures (\forall int i; 0 <= i && i < \result.length(); 
    //@     \result.charAt(i) >= 'A' && \result.charAt(i) <= 'Z');
    //@ ensures (\exists int[] indices; indices.length == \result.length();
    //@     (\forall int i; 0 <= i && i < indices.length; 
    //@         1 <= indices[i] && indices[i] <= 26) &&
    //@     columnNumber == indices[0] + 
    //@         (\sum int k; 1 <= k && k < indices.length; indices[k] * 26^k));
    public String convertToTitle(int columnNumber) {
        StringBuffer sb = new StringBuffer();
        //@ maintaining columnNumber > 0 || (columnNumber == 0 && sb.length() > 0);
        //@ maintaining (\exists int[] indices; indices.length == sb.length();
        //@     (\forall int i; 0 <= i && i < indices.length; 
        //@         1 <= indices[i] && indices[i] <= 26) &&
        //@     (original(columnNumber) == indices[0] + 
        //@         (\sum int k; 1 <= k && k < indices.length; indices[k] * 26^k) +
        //@         columnNumber * 26^(sb.length())));
        //@ decreases columnNumber;
        while (columnNumber > 0) {
            int a0 = (columnNumber - 1) % 26 + 1;
            sb.append((char)(a0 - 1 + 'A'));
            columnNumber = (columnNumber - a0) / 26;
        }
        return sb.reverse().toString();
    }
}
