class ConvertToKelvin {
    /*@
      @ requires celsius >= -273.15;
      @ ensures \result == celsius + 273.15;
      @*/
    public double convertTemperature(double celsius) {
        return celsius + 273.15;
    }
}
