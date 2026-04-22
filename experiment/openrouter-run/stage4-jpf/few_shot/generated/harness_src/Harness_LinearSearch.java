import gov.nasa.jpf.vm.Verify;

public class Harness_LinearSearch {
  public static void main(String[] args) throws Exception {
    int search = gov.nasa.jpf.vm.Verify.getInt(-4, 4);
    int len1 = gov.nasa.jpf.vm.Verify.getInt(0, 4);
    int[] array = new int[len1];
    for (int __i1 = 0; __i1 < len1; __i1++) {
      array[__i1] = gov.nasa.jpf.vm.Verify.getInt(-3, 3);
    }
    int __r = LinearSearch.linearSearch(search, array);
  }
}
