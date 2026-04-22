import java.util.Arrays;

public class SortLibrary {
    /*@ requires arr != null;
        ensures \forall int i, j;
                   0 <= i <= j < arr.length ==> arr[i] <= arr[j];
        assignable arr[*];
    @*/
    public static void sort(int[] arr) {
        Arrays.sort(arr);
    }
}
