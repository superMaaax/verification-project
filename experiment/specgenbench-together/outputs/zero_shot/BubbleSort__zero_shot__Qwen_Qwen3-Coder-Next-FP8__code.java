public class BubbleSort { 
    
    /*@ 
      @ requires array != null;
      @ requires 0 <= x && x < array.length;
      @ requires 0 <= y && y < array.length;
      @ ensures \forall int k; 0 <= k && k < array.length; 
      @     (k != x && k != y) ==> (array[k] == \old(array[k]));
      @ ensures array[x] == \old(array[y]);
      @ ensures array[y] == \old(array[x]);
      @ assignable array[x], array[y];
      @/
    void swap(int x, int y,  int array[]) {
        int temp;     
        temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    /*@ 
      @ requires arr != null;
      @ ensures \forall int i; 0 <= i && i < arr.length-1 ==> arr[i] <= arr[i+1];
      @ ensures \exists int[] sorted; 
      @     sorted.length == arr.length && 
      @     (\forall int i; 0 <= i && i < arr.length; 
      @         (\exists int j; 0 <= j && j < arr.length; sorted[i] == arr[j])) &&
      @     (\forall int i; 0 <= i && i < arr.length; 
      @         (\exists int j; 0 <= j && j < arr.length; arr[i] == sorted[j]));
      @ assignable arr[0..arr.length-1];
      @/
    int[] bubbleSort(int arr[]) {
        int n = arr.length;

        /*@ 
          @ loop_invariant 0 <= i && i <= n;
          @ loop_invariant \forall int k; n-i <= k && k < n-1 ==> arr[k] <= arr[k+1];
          @ loop_invariant \exists int[] sorted; 
          @     sorted.length == arr.length && 
          @     (\forall int p; 0 <= p && p < arr.length; 
          @         (\exists int q; 0 <= q && q < arr.length; sorted[p] == arr[q])) &&
          @     (\forall int p; 0 <= p && p < arr.length; 
          @         (\exists int q; 0 <= q && q < arr.length; arr[p] == sorted[q]));
          @ loop_invariant \forall int k; 0 <= k && k < n-i; 
          @     \exists int j; 0 <= j && j < n; arr[k] == \old(arr[j]);
          @ decreases n - i;
          @ assignable arr[0..arr.length-1];
          @/
        for (int i = 0; i < n-1; i++) {	

            /*@ 
              @ loop_invariant 0 <= j && j <= n-i-1;
              @ loop_invariant \forall int k; n-i <= k && k < n-1 ==> arr[k] <= arr[k+1];
              @ loop_invariant \exists int[] sorted; 
              @     sorted.length == arr.length && 
              @     (\forall int p; 0 <= p && p < arr.length; 
              @         (\exists int q; 0 <= q && q < arr.length; sorted[p] == arr[q])) &&
              @     (\forall int p; 0 <= p && p < arr.length; 
              @         (\exists int q; 0 <= q && q < arr.length; arr[p] == sorted[q]));
              @ loop_invariant \forall int k; 0 <= k && k <= j; 
              @     arr[k] == \old(arr[k]) || arr[k] == \old(arr[k+1]) || arr[k] == \old(arr[k-1]);
              @ loop_invariant \forall int k; j < k && k < n-i; 
              @     \exists int m; 0 <= m && m < n; arr[k] == \old(arr[m]);
              @ decreases n-i-1-j;
              @ assignable arr[0..arr.length-1];
              @/
            for (int j = 0; j < n-i-1; j++) {
                /*@ 
                  @ requires arr[j+1] < arr[j];
                  @ ensures arr[j] == \old(arr[j+1]);
                  @ ensures arr[j+1] == \old(arr[j]);
                  @ ensures \forall int k; 0 <= k && k < arr.length && k != j && k != j+1 ==> arr[k] == \old(arr[k]);
                  @ assignable arr[j], arr[j+1];
                  @/
                if (arr[j+1] < arr[j]) {  
		     swap(j, j + 1, arr); 
                } 
	    }
	} 
	return arr;
    } 
}
