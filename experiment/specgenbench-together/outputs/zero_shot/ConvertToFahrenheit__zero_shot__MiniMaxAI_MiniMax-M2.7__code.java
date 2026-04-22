/*@ 
  @ public class ConvertToFahrenheit {
  @ */
class ConvertToFahrenheit {
    /*@
      @ requires true;
      @ ensures \result == celsius * 1.80 + 32.00;
      @*/
    public double convertTemperature(double celsius) {
        return celsius * 1.80 + 32.00;
    }
}
