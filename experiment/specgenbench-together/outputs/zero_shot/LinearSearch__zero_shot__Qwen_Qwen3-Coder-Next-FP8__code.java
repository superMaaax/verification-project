public class LinearSearch {
     private static  int location;

      /*@ 
        @ requires search != \null;
        @ requires array != \null;
        @ ensures (\forall int i; 0 <= i && i < array.length; array[i] != search) <==> (\result == -1);
        @ ensures (\exists int i; 0 <= i && i < array.length; array[i] == search) <==> (\result != -1);
        @ ensures (\exists int i; 0 <= i && i < array.length; array[i] == search) ==> 
        @     (\result >= 0 && array[\result] == search && 
        @     (\forall int j; 0 <= j && j < \result; array[j] != search));
        @ assignable location;
        @*/
      public static int linearSearch(int search, int array[]) {
	      int c;

        /*@ 
          @ loop_invariant 0 <= c && c <= array.length;
          @ loop_invariant (\forall int i; 0 <= i && i < c; array[i] != search);
          @ loop_invariant location == -1 || (location >= 0 && location < c && array[location] == search);
          @ decreases array.length - c;
          @ assignable location, c;
          @*/
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
