import java.util.Arrays;

public class SortLibrary {
    /*@ spec_public @*/
    public static void sort(int[] arr) {
        requires arr != null;
        ensures \sorted(arr);
    }
}
