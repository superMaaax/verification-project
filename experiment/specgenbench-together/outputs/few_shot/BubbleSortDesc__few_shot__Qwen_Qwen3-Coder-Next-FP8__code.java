public class BubbleSortDesc { 
    
    //@ requires 0 <= x && x < array.length && 0 <= y && y < array.length;
    //@ ensures array[x] == \old(array[y]) && array[y] == \old(array[x]) && 
    //@         (\forall int k; 0 <= k && k < array.length && k != x && k != y; array[k] == \old(array[k]));
    void swap(int x, int y,  int array[]) {
        int temp;     
        temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    //@ ensures (\forall int i; 0 <= i && i < arr.length-1; arr[i] >= arr[i+1]);
    //@ ensures (\forall int i; 0 <= i && i < arr.length; \old(arr[i]) == arr[\permute(i)]);
    int[] bubbleSort(int arr[]) {
        int n = arr.length;

        //@ maintaining 0 <= i && i <= n;
        for (int i = 0; i < n-1; i++) {	
            //@ maintaining 0 <= j && j <= n-i;
            for (int j = 0; j < n-i-1; j++) {
                if (arr[j+1] > arr[j]) {  
                    swap(j, j + 1, arr); 
                } 
            }
        } 
        return arr;
    } 
}
