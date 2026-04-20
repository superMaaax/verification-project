public class OddEven {	
 
	/*@ requires true;
	  @ ensures \result <==> (x % 2 == 0);
	  @ ensures \result <==> (\exists int k; x == 2 * k);
	  @*/
	 public  boolean isEven(int x) { 
         	return x%2 == 0;
       	 } 

	/*@ requires true;
	  @ ensures \result <==> (x % 2 != 0);
	  @ ensures \result <==> (\exists int k; x == 2 * k + 1);
	  @*/
	 public  boolean isOdd(int x) { 
         	return x%2 != 0;
       	 } 
}
