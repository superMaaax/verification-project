public class RepeatedNumNested {

    //@ requires arr != null;
    //@ ensures -1 <= \result && \result < arr.length;
    //@ ensures \result == -1 ==> (\forall int i; 0 <= i && i < arr.length; (\forall int j; i < j && j < arr.length; arr[i] != arr[j]));
    public static int repeatedNum(int[] arr) {
        //@ maintaining 0 <= i && i <= arr.length;
        //@ decreases arr.length - i;
        for (int i = 0; i < arr.length; ++i) {
            //@ maintaining i < j && j <= arr.length;
            //@ decreases arr.length - j;
            for (int j = i + 1; j < arr.length; ++j) {
                if(arr[i] == arr[j])
                    return i;
            }
        }
        return -1;
    }
    
}
