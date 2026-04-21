  public class LinearSearch {
     private static  int location;

      //@ requires array != null;
      //@ ensures (\exists int k; 0 <= k && k < array.length; array[k] == search) ==>
      //@   (0 <= \result && \result < array.length && array[\result] == search);
      //@ ensures !(\exists int k; 0 <= k && k < array.length; array[k] == search) ==> \result == -1;
      public static int linearSearch(int search, int array[]) {
	      int c;

        //@ maintaining 0 <= c && c <= array.length;
        //@ maintaining (\forall int k; 0 <= k && k < c; array[k] != search);
        //@ decreases array.length - c;
        for (c = 0; c < array.length; c++) {  
          if (array[c] == search) {
	      location = c;
              break;
          }
        }
       if (c == array.length) {
            location = -1;
       }
     return location;
     }
  }
