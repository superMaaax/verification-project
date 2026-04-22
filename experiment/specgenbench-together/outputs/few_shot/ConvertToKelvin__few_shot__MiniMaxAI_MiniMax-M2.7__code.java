class ConvertToKelvin {
    //@ requires !Double.isNaN(celsius);
    //@ requires -273.15 <= celsius;
    //@ ensures \result == celsius + 273.15;
    public double convertTemperature(double celsius) {
        return celsius + 273.15;
    }
}
