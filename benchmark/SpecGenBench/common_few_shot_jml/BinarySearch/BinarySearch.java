public class BinarySearch {

    //@ requires arr != null;
    //@ requires (\forall int p, q; 0 <= p && p <= q && q < arr.length; arr[p] <= arr[q]);
    //@ ensures \result == -1 ==> (\forall int k; 0 <= k && k < arr.length; arr[k] != key);
    //@ ensures 0 <= \result && \result < arr.length ==> arr[\result] == key;
    public static int Binary(int[] arr, int key) {
        if (arr.length == 0) {
            return -1;
        } else {
            int low = 0;
            int high = arr.length;
            int mid =  high / 2;

            //@ maintaining 0 <= low && low <= high && high <= arr.length;
            //@ maintaining (\forall int k; 0 <= k && k < low; arr[k] < key);
            //@ maintaining (\forall int k; high <= k && k < arr.length; key < arr[k]);
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
