public class SelectionSort {

    /*@ public normal_behavior
      @ requires arr != null;
      @ ensures (\forall int i; i >= 0 && i < arr.length - 1; arr[i] <= arr[i+1]);
      @*/
    public static void selectionSort(int[] arr){  
        for (int i = 0; i < arr.length - 1; i++)  
        {  
            /*@ spec_public @*/ int index = i;  
            for (int j = i + 1; j < arr.length; j++){  
                if (arr[j] < arr[index]){  
                    index = j;
                }  
            }  
            /*@ spec_public @*/ int smallerNumber = arr[index];   
            arr[index] = arr[i];  
            arr[i] = smallerNumber;  
        }  
    }
    
}
