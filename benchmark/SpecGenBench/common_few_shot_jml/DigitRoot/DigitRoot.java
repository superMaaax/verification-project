class DigitRoot {
    //@ requires num >= 0;
    //@ ensures 0 <= \result && \result < 10;
    public int digitRoot(int num) {
        while (num >= 10) {
            int sum = 0;
            //@ maintaining sum >= 0;
            //@ decreases num;
            while (num > 0) {
                sum += num % 10;
                num /= 10;
            }
            num = sum;
        }
        return num;
    }
}
