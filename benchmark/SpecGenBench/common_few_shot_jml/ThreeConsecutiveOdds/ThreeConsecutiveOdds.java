public class ThreeConsecutiveOdds {

    //@ requires arr != null;
    //@ ensures \result <==> (\exists int i; 0 <= i && i <= arr.length - 3; (arr[i] & 1) != 0 && (arr[i + 1] & 1) != 0 && (arr[i + 2] & 1) != 0);
    public boolean threeConsecutiveOdds(int[] arr) {
        int n = arr.length;

        //@ maintaining 0 <= i && (n < 3 || i <= n - 2);
        //@ decreases (n >= 3 ? n - 2 - i : 0);
        for (int i = 0; i <= n - 3; ++i) {
            if ((arr[i] & 1) != 0 && (arr[i + 1] & 1) != 0 && (arr[i + 2] & 1) != 0) {
                return true;
            }
        }
        return false;
    }
}
