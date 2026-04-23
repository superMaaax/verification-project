public class FindFirstZero {

    	 //@ requires x != null;
    	 //@ ensures x.length == 0 ==> \result == -1;
    	 //@ ensures x.length > 0 && (\exists int k; 0 <= k && k < x.length; x[k] == 0) ==>
    	 //@   0 <= \result && \result < x.length && x[\result] == 0;
    	 //@ ensures x.length > 0 && !(\exists int k; 0 <= k && k < x.length; x[k] == 0) ==> \result == -1;
    	 public static int FindFirstZero(int[] x) {
         	assert x.length >= 0;
         	if (x.length == 0) {
            		return -1;
        	} else {
            		int index = 0;

            		//@ maintaining 0 <= index && index <= x.length;
            		//@ maintaining (\forall int k; 0 <= k && k < index; x[k] != 0);
            		//@ decreases x.length - index;
            		while (x.length - index > 0 && x[index] != 0) {
                		index = index + 1;
            		}
            		if (x.length - index == 0) {
               			index = -1;
            		}
            	return index;
        	}
    	}
}

