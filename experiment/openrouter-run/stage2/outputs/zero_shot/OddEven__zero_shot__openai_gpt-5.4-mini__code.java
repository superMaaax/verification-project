public class OddEven {	
 
	/*@ public normal_behavior
	  @ requires true;
	  @ ensures \result <==> (x % 2 == 0);
	  @ assignable \nothing;
	  @*/
	 public  boolean isEven(int x) { 
         	return x%2 == 0;
       	 } 

	/*@ public normal_behavior
	  @ requires true;
	  @ ensures \result <==> (x % 2 != 0);
	  @ assignable \nothing;
	  @*/
	 public  boolean isOdd(int x) { 
         	return x%2 != 0;
       	 } 
}
