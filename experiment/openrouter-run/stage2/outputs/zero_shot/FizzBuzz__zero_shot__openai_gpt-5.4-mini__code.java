class FizzBuzz {
    /*@ public normal_behavior
      @   requires true;
      @   ensures (\result == 0) || (\result == 3) || (\result == 5) || (\result == 8);
      @   ensures (\result == 3) <==> (n % 3 == 0 && n % 5 != 0);
      @   ensures (\result == 5) <==> (n % 5 == 0 && n % 3 != 0);
      @   ensures (\result == 8) <==> (n % 3 == 0 && n % 5 == 0);
      @*/
    public int fizzBuzz(int n) {
        int res = 0;
        if (n % 3 == 0) {
            res += 3;
        }
        if (n % 5 == 0) {
            res += 5;
        }
        return res;
    }
}
