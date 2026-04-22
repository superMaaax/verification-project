import gov.nasa.jpf.vm.Verify;

public class Harness_IsPrefix {
  public static void main(String[] args) throws Exception {
    int slen0 = gov.nasa.jpf.vm.Verify.getInt(0, 2);
    char[] __cb0 = new char[slen0];
    for (int __j0 = 0; __j0 < slen0; __j0++) {
      __cb0[__j0] = (char) gov.nasa.jpf.vm.Verify.getInt(97, 122);
    }
    String pat = new String(__cb0);
    int slen1 = gov.nasa.jpf.vm.Verify.getInt(0, 2);
    char[] __cb1 = new char[slen1];
    for (int __j1 = 0; __j1 < slen1; __j1++) {
      __cb1[__j1] = (char) gov.nasa.jpf.vm.Verify.getInt(97, 122);
    }
    String txt = new String(__cb1);
    boolean __r = new IsPrefix().isPrefix(pat, txt);
  }
}
