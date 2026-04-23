public class BubbleSort { 
    
    //@ requires array != null && 0 <= x && x < array.length && 0 <= y && y < array.length;
    //@ ensures array[x] == \old(array[y]) && array[y] == \old(array[x]);
    void swap(int x, int y,  int array[]) {
        int temp;     
        temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    //@ requires arr != null;
    //@ ensures (\forall int p; 0 <= p && p < arr.length - 1; arr[p] <= arr[p + 1]);
    int[] bubbleSort(int arr[]) {
        int n = arr.length;

        //@ maintaining 0 <= i && i <= n - 1;
        //@ maintaining (\forall int p; 0 <= p && p < n - i - 1; arr[p] <= arr[p + 1]);
        //@ maintaining (\forall int p; n - i - 1 <= p && p < n - 1; arr[p] <= arr[p + 1]);
        //@ decreases (n - 1) - i;
        for (int i = 0; i < n-1; i++) {	

            //@ maintaining 0 <= j && j <= n - i - 1;
            //@ maintaining (\forall int p; 0 <= p && p < j; arr[p] <= arr[p + 1]);
            //@ decreases (n - i - 1) - j;
            for (int j = 0; j < n-i-1; j++) {
                if (arr[j+1] < arr[j]) {  
		     swap(j, j + 1, arr); 
                } 
	    }
	} 
	return arr;
    } 
}
