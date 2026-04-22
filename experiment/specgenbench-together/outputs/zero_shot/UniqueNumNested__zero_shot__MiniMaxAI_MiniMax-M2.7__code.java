public class UniqueNumNested {

    /*@ public normal_behavior
      @ requires arr != null;
      @ ensures \result == -1 || 
      @         (\result >= 0 && \result < arr.length && 
      @          (\forall int k; k >= 0 && k < arr.length; 
      @                   k != \result ==> arr[k] != arr[\result]));
      @*/
    public static int uniqueNum(int[] arr) {
        for (int i = 0; i < arr.length; ++i) {
            int j = 0;
            while(j < arr.length) {
                if(i != j && arr[i] == arr[j])
                    break;
                j++;
            }
            if(j == arr.length)
                return i;
        }
        return -1;
    }
    
}
