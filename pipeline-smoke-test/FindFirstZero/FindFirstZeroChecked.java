public class FindFirstZeroChecked {

    public static int FindFirstZero(int[] x) {
        int result = FindFirstZero.FindFirstZero(x);

        if (x.length == 0 && result != -1) {
            throw new AssertionError("Expected -1 for empty array");
        }

        if (0 <= result && result < x.length) {
            if (x[result] != 0) {
                throw new AssertionError("Result index does not point to zero");
            }
            for (int i = 0; i < result; i++) {
                if (x[i] == 0) {
                    throw new AssertionError("Result is not the first zero");
                }
            }
        }

        if (result == -1) {
            for (int i = 0; i < x.length; i++) {
                if (x[i] == 0) {
                    throw new AssertionError("Returned -1 even though a zero exists");
                }
            }
        }

        return result;
    }
}
