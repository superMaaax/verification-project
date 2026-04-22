import gov.nasa.jpf.vm.Verify;

public class Harness_IsAllUnique {
  public static void main(String[] args) throws Exception {
    int slen0 = gov.nasa.jpf.vm.Verify.getInt(0, 2);
    char[] __cb0 = new char[slen0];
    for (int __j0 = 0; __j0 < slen0; __j0++) {
      __cb0[__j0] = (char) gov.nasa.jpf.vm.Verify.getInt(97, 122);
    }
    String str = new String(__cb0);
    boolean __r = new IsAllUnique().isAllUnique(str);
  }
}
