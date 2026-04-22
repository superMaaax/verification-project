public class SelectionSortDesc {

    //@ requires arr != null;
    //@ ensures (\forall int i; 0 <= i && i < arr.length - 1; arr[i] >= arr[i+1]);
    //@ ensures (\forall int i; 0 <= i && i < arr.length; (\exists int j; 0 <= j && j < arr.length; arr[i] == \old(arr[j])));
    public static void selectionSort(int[] arr){  
        for (int i = 0; i < arr.length - 1; i++)  
        {  
            int index = i;  
            //@ maintaining 0 <= i && i < arr.length - 1;
            //@ maintaining (\forall int p; 0 <= p && p < i; arr[p] >= arr[p+1]);
            //@ maintaining (\forall int p; 0 <= p && p < i; (\forall int q; i <= q && q < arr.length; arr[p] >= arr[q]));
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
