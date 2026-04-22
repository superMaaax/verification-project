public class BinarySearch {

    //@ requires arr != null;
    //@ ensures arr.length == 0 ==> \result == -1;
    //@ ensures \result != -1 ==> (0 <= \result && \result < arr.length && arr[\result] == key);
    //@ ensures \result == -1 ==> (arr.length == 0 || (\forall int i; 0 <= i && i < arr.length; arr[i] != key));
    public static int Binary(int[] arr, int key) {
        if (arr.length == 0) {
            return -1;
        } else {
            int low = 0;
            int high = arr.length;
            int mid = high / 2;

            //@ maintaining 0 <= low && low <= high && high <= arr.length;
            //@ maintaining low <= mid && mid < high;
            //@ maintaining (\forall int i; i < low || i >= high; arr[i] != key);
            //@ decreases high - low;
            while (low < high && arr[mid] != key) {
                if (arr[mid] < key) {
                    low = mid + 1;
                } else {
                    high = mid;
                }
                mid = low + (high - low) / 2;
            }
            if (low >= high) {
                return -1;
            }
            return mid;
        }
    }
}

But we need to check the loop invariants: The condition
