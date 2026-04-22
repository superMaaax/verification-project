public class IntCube {

    //@ requires x != Integer.MIN_VALUE;
    //@ ensures \result == x * x * x;
    public static int cubeOf(int x) {
        boolean neg = false;
        if(x < 0) {
            neg = true;
            x = -x;
        }
        int res = 0;
        //@ maintaining 0 <= i && i <= x;
        //@ maintaining res == i * x * x;
        //@ decreases x - i;
        for(int i = 0; i < x; i++) {
            //@ maintaining 0 <= j && j <= x;
            //@ maintaining res == i * x * x + j * x;
            //@ decreases x - j;
            for(int j = 0; j < x; j++) {
                //@ maintaining 0 <= k && k <= x;
                //@ maintaining res == i * x * x + j * x + k;
                //@ decreases x - k;
                for(int k = 0; k < x; k++) {
                    res = res + 1;
                }
            }
        }
        return (neg ? -res : res);
    }

}
