public class BubbleSort { 
    
    //@ requires 0 <= x && x < array.length && 0 <= y && y < array.length;
    //@ ensures array[x] == \old(array[y]) && array[y] == \old(array[x]) && 
    //@         (\forall int k; 0 <= k && k < array.length && k != x && k != y; array[k] == \old(array[k]));
    void swap(int x, int y,  int array[]) {
        int temp;     
        temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    //@ ensures (\forall int i; 0 <= i && i < arr.length-1; arr[i] <= arr[i+1]);
    //@ ensures (\exists int[] original; \old(arr) == original && 
    //@          (\forall int i; 0 <= i && i < arr.length; 
    //@           (\exists int j; 0 <= j && j < original.length; arr[i] == original[j])));
    int[] bubbleSort(int arr[]) {
        int n = arr.length;

        //@ maintaining 0 <= i && i <= n;
        //@ maintaining (\forall int k; i <= k && k < n-1; arr[k] <= arr[k+1]);
        for (int i = 0; i < n-1; i++) {	
            //@ maintaining 0 <= j && j <= n-i-1;
            //@ maintaining (\forall int k; j <= k && k < n-i-1; arr[k] <= arr[k+1]);
            //@ decreases n-i-1-j;
            for (int j = 0; j < n-i-1; j++) {
                if (arr[j+1] < arr[j]) {  
		     swap(j, j + 1, arr); 
                } 
	    }
	} 
	return arr;
    } 
}
