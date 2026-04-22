public class RepeatedNumNested {

    //@ requires arr != null;
    //@ ensures (\result == -1) <==> (\forall int i, j; 0 <= i && i < j && j < arr.length; arr[i] != arr[j]);
    //@ ensures \result != -1 ==> 0 <= \result && \result < arr.length;
    //@ ensures \result != -1 ==> (\exists int j; \result < j && j < arr.length; arr[\result] == arr[j]);
    public static int repeatedNum(int[] arr) {
        /*@
          @ maintaining 0 <= i && i <= arr.length;
          @ maintaining (\forall int p, q; 0 <= p && p < q && p < i && q < arr.length; arr[p] != arr[q]);
          @ decreases arr.length - i;
          @*/
        for (int i = 0; i < arr.length; ++i) {
            /*@
              @ maintaining i + 1 <= j && j <= arr.length;
              @ maintaining (\forall int q; i + 1 <= q && q < j; arr[i] != arr[q]);
              @ decreases arr.length - j;
              @*/
            for (int j = i + 1; j < arr.length; ++j) {
                if(arr[i] == arr[j])
                    return i;
            }
        }
        return -1;
    }
    
}
