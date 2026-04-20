public class BubbleSortDesc { 
    
    /*@ 
      @ requires array != null;
      @ requires 0 <= x && x < array.length;
      @ requires 0 <= y && y < array.length;
      @ ensures array[x] == \old(array[y]) && array[y] == \old(array[x]);
      @ ensures (\forall int k; 0 <= k && k < array.length; 
      @     (k != x && k != y) ==> array[k] == \old(array[k]));
      @/
    void swap(int x, int y,  int array[]) {
        int temp;     
        temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    /*@ 
      @ requires arr != null;
      @ ensures (\forall int i; 0 <= i && i < arr.length-1; arr[i] >= arr[i+1]);
      @ ensures (\forall int i; 0 <= i && i < arr.length; 
      @     (\exists int j; 0 <= j && j < arr.length; arr[i] == \old(arr[j])));
      @ ensures (\forall int i; 0 <= i && i < arr.length; 
      @     (\exists int j; 0 <= j && j < arr.length; \old(arr[i]) == arr[j]));
      @/
    int[] bubbleSort(int arr[]) {
        int n = arr.length;

        /*@ 
          @ loop_invariant 0 <= i && i <= n;
          @ loop_invariant (\forall int k; i <= k && k < n-1; 
          @     (\forall int l; 0 <= l && l < n-k-1; arr[l] >= arr[l+1]));
          @ loop_invariant (\forall int k; 0 <= k && k < i; 
          @     (\forall int l; 0 <= l && l < n-k-1; arr[l] >= arr[l+1]));
          @ decreases n - i;
          @/
        for (int i = 0; i < n-1; i++) {	

            /*@ 
              @ loop_invariant 0 <= j && j <= n-i-1;
              @ loop_invariant (\forall int k; 0 <= k && k < j; arr[k] >= arr[k+1]);
              @ loop_invariant (\forall int k; j < k && k < n-i-1; 
              @     arr[k] >= arr[k+1] || arr[k+1] >= arr[k]);
              @ loop_invariant (\forall int k; 0 <= k && k < i; 
              @     (\forall int l; 0 <= l && l < n-k-1; arr[l] >= arr[l+1]));
              @ decreases n-i-1-j;
              @/
            for (int j = 0; j < n-i-1; j++) {
                if (arr[j+1] > arr[j]) {  
		     swap(j, j + 1, arr); 
                } 
	    }
	} 
	return arr;
    } 
}
