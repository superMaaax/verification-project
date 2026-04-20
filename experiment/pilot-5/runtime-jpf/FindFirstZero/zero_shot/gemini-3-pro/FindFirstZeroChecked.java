public class FindFirstZeroChecked {

    public static int FindFirstZero(int[] x) {
        int result = FindFirstZero.FindFirstZero(x);

        if (x == null) {
            throw new AssertionError("Precondition violated: x != null");
        }

        if (result == -1) {
            for (int i = 0; i < x.length; i++) {
                if (x[i] == 0) {
                    throw new AssertionError("Postcondition violated: returned -1 although zero exists");
                }
            }
        }

        if (result >= 0) {
            if (!(0 <= result && result < x.length)) {
                throw new AssertionError("Postcondition violated: result out of bounds");
            }
            if (x[result] != 0) {
                throw new AssertionError("Postcondition violated: result does not point to zero");
            }
            for (int i = 0; i < result; i++) {
                if (x[i] == 0) {
                    throw new AssertionError("Postcondition violated: result is not first zero");
                }
            }
        }

        return result;
    }
}
