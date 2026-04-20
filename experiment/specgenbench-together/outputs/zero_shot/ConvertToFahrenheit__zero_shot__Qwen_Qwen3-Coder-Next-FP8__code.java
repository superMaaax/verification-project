class ConvertToFahrenheit {
    /*@ spec_public @*/
    public double convertTemperature(double celsius) {
        requires celsius != Double.NaN;
        ensures \result == celsius * 1.80 + 32.00;
        ensures \result != Double.NaN;
    }
}
