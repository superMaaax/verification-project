import gov.nasa.jpf.vm.Verify;

public class FindFirstZeroDriver {
    public static void main(String[] args) {
        int len = Verify.getInt(0, 2);
        int[] x = new int[len];

        for (int i = 0; i < len; i++) {
            x[i] = Verify.getInt(-1, 1);
        }

        FindFirstZeroChecked.FindFirstZero(x);
    }
}
