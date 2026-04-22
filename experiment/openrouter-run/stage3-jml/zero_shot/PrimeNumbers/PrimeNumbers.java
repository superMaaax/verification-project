public class PrimeNumbers
{
    
    /*@ public normal_behavior
      @ requires d != 0;
      @ ensures \result <==> (n % d == 0);
      @*/
    private  static boolean div(int n, int d) { return n%d == 0; }

    private  int primeArray;
    
    /*@ public normal_behavior
      @ requires n > 0;
      @ ensures \result != null;
      @ ensures \result.length == n;
      @ ensures \result[0] == 2;
      @ ensures primeArray == \result;
      @*/
    public int[] primeList(int n)
    {
      int status = 1, num = 3, count, j;
      primeArray = new int[n];
      primeArray[0] = 2;

      /*@ maintaining 2 <= count && count <= n + 1;
        @ maintaining num >= 3;
        @ maintaining status == 0 || status == 1;
        @ decreases n - count + 1;
        @*/
      for (count = 2; count <= n;)
      { 

         /*@ maintaining 2 <= j && j <= num/2 + 1;
           @ maintaining status == 0 || status == 1;
           @ decreases num/2 - j + 1;
           @*/
         for (j = 2; j <= num/2; j++)
         { 
            if (div(num,j))
            {
               status = 0;
               break;
            }
         }

         if (status != 0)
         {  
            primeArray[count - 1] = num;
            count++;
	
         }
         status = 1;
     
         num++;
      } 
	      return primeArray; 
   }
}
