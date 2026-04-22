public class SelectionSort {

    /*@ public normal_behavior
      @ requires arr != null;
      @ ensures (\forall int i; 0 <= i && i < arr.length - 1; arr[i] <= arr[i + 1]);
      @ assignable arr[*];
      @*/
    public static void selectionSort(int[] arr){  
        /*@ loop_invariant 0 <= i && i <= arr.length - 1;
          @ loop_invariant (\forall int m; 0 <= m && m < i; (\forall int n; m < n && n < arr.length; arr[m] <= arr[n]));
          @ decreases arr.length - i;
          @*/
        for (int i = 0; i < arr.length - 1; i++)  
        {  
            int index = i;  
            /*@ loop_invariant i + 1 <= j && j <= arr.length;
              @ loop_invariant i <= index && index < arr.length;
              @ loop_invariant (\forall int k; i <= k && k < j; arr[index] <= arr[k]);
              @ decreases arr.length - j;
              @*/
            for (int j = i + 1; j < arr.length; j++){  
                if (arr[j] < arr[index]){  
                    index = j;
                }  
            }  
            int smallerNumber = arr[index];   
            arr[index] = arr[i];  
            arr[i] = smallerNumber;  
        }  
    }
    
}
