
    public class PrimeNumbers
    {
        
        //@ requires d != 0;
        //@ ensures \result <==> n % d == 0;
        private  static boolean div(int n, int d) { return n%d == 0; }

        private  int primeArray[];
        
        //@ requires n >= 1;
        //@ ensures \result != null && \result.length == n;
        public int[] primeList(int n)
        {
          int status = 1, num = 3, count, j;
          primeArray = new int[n];
          primeArray[0] = 2;
  
          //@ maintaining 2 <= count && count <= n + 1;
          //@ maintaining num >= 3;
          for (count = 2; count <= n;)
          { 

             //@ maintaining 2 <= j && j <= num / 2 + 1;
             //@ maintaining (\forall int k; 2 <= k && k < j; num % k != 0);
             //@ decreases num / 2 + 1 - j;
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
