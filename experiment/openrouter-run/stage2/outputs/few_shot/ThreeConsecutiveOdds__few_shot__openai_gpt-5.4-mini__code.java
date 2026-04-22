public class ThreeConsecutiveOdds {

    //@ requires arr != null;
    //@ ensures \result <==> (\exists int i; 0 <= i && i + 2 < arr.length; (arr[i] & 1) != 0 && (arr[i + 1] & 1) != 0 && (arr[i + 2] & 1) != 0);
    public boolean threeConsecutiveOdds(int[] arr) {
        int n = arr.length;

        //@ maintaining 0 <= i && i <= n - 2;
        //@ maintaining n == arr.length;
        //@ decreases n - 2 - i;
        for (int i = 0; i <= n - 3; ++i) {
            if ((arr[i] & 1) != 0 && (arr[i + 1] & 1) != 0 && (arr[i + 2] & 1) != 0) {
                return true;
            }
        }
        return false;
    }
}
