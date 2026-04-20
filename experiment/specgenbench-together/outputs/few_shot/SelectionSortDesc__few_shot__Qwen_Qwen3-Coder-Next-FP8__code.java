public class SelectionSortDesc {

    //@ ensures (\forall int i; 0 <= i && i < arr.length - 1; arr[i] >= arr[i + 1]);
    //@ ensures (\exists int[] perm; perm.length == arr.length && 
    //@   (\forall int i; 0 <= i && i < arr.length; 
    //@     (\exists int k; 0 <= k && k < arr.length; perm[k] == arr[i])));
    public static void selectionSort(int[] arr){  
        for (int i = 0; i < arr.length - 1; i++)  
        {  
            int index = i;  
            //@ maintaining 0 <= i && i < arr.length;
            //@ maintaining 0 <= index && index < arr.length;
            //@ maintaining (\forall int j; i < j && j < index; arr[index] >= arr[j]);
            //@ decreases arr.length - i;
            for (int j = i + 1; j < arr.length; j++){  
                if (arr[j] > arr[index]){  
                    index = j;
                }  
            }  
            int smallerNumber = arr[index];   
            arr[index] = arr[i];  
            arr[i] = smallerNumber;  
        }  
    }
    
}
