public class ThreeConsecutiveOdds {

    //@ ensures \result <==> (\exists int i; 0 <= i && i <= arr.length - 3;
    //@     (arr[i] & 1) != 0 && (arr[i + 1] & 1) != 0 && (arr[i + 2] & 1) != 0);
    public boolean threeConsecutiveOdds(int[] arr) {
        int n = arr.length;

        //@ maintaining 0 <= i && i <= n - 2;
        //@ maintaining (\forall int j; 0 <= j && j < i;
        //@     !((arr[j] & 1) != 0 && (arr[j + 1] & 1) != 0 && (arr[j + 2] & 1) != 0));
        //@ decreases n - i - 2;
        for (int i = 0; i <= n - 3; ++i) {
            if ((arr[i] & 1) != 0 && (arr[i + 1] & 1) != 0 && (arr[i + 2] & 1) != 0) {
                //@ ensures \result <==> (\exists int j; 0 <= j && j <= i;
                //@     (arr[j] & 1) != 0 && (arr[j + 1] & 1) != 0 && (arr[j + 2] & 1) != 0);
                return true;
            }
        }
        //@ ensures \result <==> !(\exists int i; 0 <= i && i <= n - 3;
        //@     (arr[i] & 1) != 0 && (arr[i + 1] & 1) != 0 && (arr[i + 2] & 1) != 0);
        return false;
    }
}
