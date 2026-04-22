public class BinarySearch {

    /*@ requires arr != null;
        ensures \result == -1 || (\result >= 0 && \result < arr.length);
        ensures \result != -1 ==> arr[\result] == key;
     @*/
    public static int Binary(int[] arr, int key) {
        if (arr.length == 0) {
            return -1;
        } else {
            int low = 0;
            int high = arr.length;
            int mid = high / 2;

            /*@ maintaining 0 <= low && low <= high && high <= arr.length;
                maintaining (\forall int i; 0 <= i && i < low; arr[i] < key);
                maintaining (\forall int i; high <= i && i < arr.length; arr[i] > key);
                decreases high - low;
             @*/
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
