public class ExException {
  int zero() {
    return 0;
  }

  static int test(int secret) {
    Main o = null;
    if (secret > 0) {
      o = new Main();
    } else assert false;
    int i = o.zero();
    return i;
  }
}
