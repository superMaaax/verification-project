public class BubbleSortDesc { 
    
    //@ requires array != null;
    //@ requires 0 <= x && x < array.length;
    //@ requires 0 <= y && y < array.length;
    //@ assignable array[x], array[y];
    //@ ensures array[x] == \old(array[y]);
    //@ ensures array[y] == \old(array[x]);
    //@ ensures (\forall int i; 0 <= i && i < array.length && i != x && i != y; array[i] == \old(array[i]));
    void swap(int x, int y,  int array[]) {
        int temp;     
        temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    //@ requires arr != null;
    //@ assignable arr[*];
    //@ ensures \result == arr;
    //@ ensures (\forall int i; 0 <= i && i < arr.length - 1; \result[i] >= \result[i + 1]);
    int[] bubbleSort(int arr[]) {
        int n = arr.length;

        /*@
          @ loop_invariant 0 <= i && i <= n-1;
          @ loop_invariant (\forall int k; 0 <= k && k < i; 
          @                    (\forall int m; k < m && m < n; arr[k] >= arr[m]));
          @ decreases n - 1 - i;
          @*/
        for (int i = 0; i < n-1; i++) {	

            /*@
              @ loop_invariant 0 <= j && j <= n - i - 1;
              @ loop_invariant (\forall int k; 0 <= k && k < j; arr[k] >= arr[k+1]);
              @ decreases n - i - 1 - j;
              @*/
            for (int j = 0; j < n-i-1; j++) {
                if (arr[j+1] > arr[j]) {  
		     swap(j, j + 1, arr); 
                } 
	    }
	} 
	return arr;
    } 
}
