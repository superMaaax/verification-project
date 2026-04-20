public class SelectionSort {

    /*@ spec_public @*/
    public static void selectionSort(int[] arr){  
        // Outer loop invariant: elements from 0 to i-1 are sorted and in their final positions
        /*@ loop_invariant 0 <= i && i <= arr.length - 1 &&
            @   (\forall int k; 0 <= k && k < i; 
            @       (\forall int l; k < l && l < arr.length; arr[k] <= arr[l]));
            @ loop_decreases arr.length - 1 - i;
        @*/
        for (int i = 0; i < arr.length - 1; i++)  
        {  
            int index = i;  
            // Inner loop invariant: index is the position of the minimum element in arr[i..j-1]
            /*@ loop_invariant i + 1 <= j && j <= arr.length &&
                @   (\forall int k; i + 1 <= k && k < j; arr[index] <= arr[k]);
                @ loop_decreases arr.length - j;
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
