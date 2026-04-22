class ConvertToTitle {
    //@ requires columnNumber > 0;
    //@ ensures (\result != null);
    //@ ensures (\forall int i; 0 <= i && i < \result.length(); 'A' <= \result.charAt(i) && \result.charAt(i) <= 'Z');
    public String convertToTitle(int columnNumber) {
        StringBuffer sb = new StringBuffer();
        //@ maintaining columnNumber >= 0;
        //@ maintaining sb != null;
        //@ decreases columnNumber;
        while (columnNumber > 0) {
            int a0 = (columnNumber - 1) % 26 + 1;
            sb.append((char)(a0 - 1 + 'A'));
            columnNumber = (columnNumber - a0) / 26;
        }
        return sb.reverse().toString();
    }
}
