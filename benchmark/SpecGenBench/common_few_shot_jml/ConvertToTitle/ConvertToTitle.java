class ConvertToTitle {
    //@ ensures \result != null;
    public String convertToTitle(int columnNumber) {
        StringBuffer sb = new StringBuffer();
        //@ maintaining columnNumber >= 0;
        //@ decreases columnNumber;
        while (columnNumber > 0) {
            int a0 = (columnNumber - 1) % 26 + 1;
            sb.append((char)(a0 - 1 + 'A'));
            columnNumber = (columnNumber - a0) / 26;
        }
        return sb.reverse().toString();
    }
}
