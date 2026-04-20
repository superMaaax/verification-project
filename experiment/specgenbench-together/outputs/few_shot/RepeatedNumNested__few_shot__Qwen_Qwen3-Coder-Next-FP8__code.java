public class RepeatedNumNested {
    //@ ensures \result == -1 <==> (\forall int i, j; 0 <= i && i < j && j < arr.length; arr[i] != arr[j]);
    //@ ensures -1 < \result < arr.length && (\exists int j; \result < j && j < arr.length; arr[\result] == arr[j]);
    public static int repeatedNum(int[] arr) {
        for (int i = 0; i < arr.length; ++i) {
            //@ maintaining 0 <= i && i <= arr.length;
            //@ maintaining (\forall int k, l; 0 <= k && k < i && k < l && l < arr.length; arr[k] != arr[l]);
            for (int j = i + 1; j < arr.length; ++j) {
                //@ maintaining i + 1 <= j && j <= arr.length;
                //@ maintaining (\forall int k, l; 0 <= k && k < i && k < l && l < arr.length; arr[k] != arr[l]);
                if(arr[i] == arr[j])
                    return i;
            }
        }
        return -1;
    }
    
}
