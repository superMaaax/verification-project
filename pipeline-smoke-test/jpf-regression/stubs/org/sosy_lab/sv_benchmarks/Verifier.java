package org.sosy_lab.sv_benchmarks;

public final class Verifier {
    private Verifier() {}

    public static void assume(boolean condition) {
        if (!condition) {
            throw new RuntimeException("assumption violated");
        }
    }

    public static int nondetInt() {
        return 0;
    }
}
