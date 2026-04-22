public class SelectionSort {

    //@ requires arr != null;
    //@ ensures (\forall int p; 0 <= p && p < arr.length - 1; arr[p] <= arr[p + 1]);
    public static void selectionSort(int[] arr){  
        //@ maintaining 0 <= i && i <= arr.length - 1;
        //@ maintaining (\forall int p; 0 <= p && p < i; arr[p] <= arr[p + 1]);
        //@ decreases (arr.length - 1) - i;
        for (int i = 0; i < arr.length - 1; i++)  
        {  
            int index = i;  
            //@ maintaining i < j && j <= arr.length;
            //@ maintaining i <= index && index < arr.length;
            //@ maintaining (\forall int k; i <= k && k < j; arr[index] <= arr[k]);
            //@ decreases arr.length - j;
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