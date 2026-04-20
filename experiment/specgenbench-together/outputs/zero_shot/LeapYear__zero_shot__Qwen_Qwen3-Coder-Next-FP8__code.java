public class LeapYear {
    
    /*@ spec_public @*/
    public  boolean isLeapYear(int year) {
        /*@ 
          requires \true;
          ensures (\result <==> (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)));
        @*/
        boolean leap = false;
         
        if (year % 4 == 0)
        {
            if ( year % 100 == 0)
            {
                if ( year % 400 == 0)
                    leap = true;
                else
                    leap = false;
            }
            else
                leap = true;
        }
        else
            leap = false;
	
	return leap;
   }
}
