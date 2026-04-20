class DigitRoot {
    //@ ensures \result >= 0 && \result <= 9;
    //@ ensures \result == num % 9 || (\result == 9 && num % 9 == 0 && num != 0);
    public int digitRoot(int num) {
        while (num >= 10) {
            int sum = 0;
            //@ maintaining sum >= 0;
            //@ maintaining num >= 0;
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
