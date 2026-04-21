public class UniqueNumNested {

    //@ requires arr != null;
    //@ ensures -1 <= \result && \result < arr.length;
    //@ ensures \result >= 0 ==> (\forall int j; 0 <= j && j < arr.length; j != \result ==> arr[j] != arr[\result]);
    public static int uniqueNum(int[] arr) {
        //@ maintaining 0 <= i && i <= arr.length;
        //@ decreases arr.length - i;
        for (int i = 0; i < arr.length; ++i) {
            int j = 0;
            //@ maintaining 0 <= j && j <= arr.length;
            //@ decreases arr.length - j;
            while(j < arr.length) {
                if(i != j && arr[i] == arr[j])
                    break;
                j++;
            }
            if(j == arr.length)
                return i;
        }
        return -1;
    }
    
}
