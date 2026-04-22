public class LeapYearSeq {
    
    /*@ public normal_behavior
      @ requires true;
      @ ensures \result <==> ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0)));
      @ assignable \nothing;
      @*/
    public  boolean isLeapYear(int year) {
        return ((year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? true : false) : true) : false);
   }
}
