public class BubbleSort { 
    
    /*@
      requires array != null;
      requires 0 <= x && x < array.length;
      requires 0 <= y && y < array.length;
      ensures array[x] == \old(array[y]);
      ensures array[y] == \old(array[x]);
      ensures \forall int
