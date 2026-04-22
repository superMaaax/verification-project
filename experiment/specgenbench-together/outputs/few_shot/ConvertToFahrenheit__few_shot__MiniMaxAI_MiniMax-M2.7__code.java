class ConvertToFahrenheit {
    //@ requires Double.NEGATIVE_INFINITY < celsius && celsius < Double.POSITIVE_INFINITY;
    //@ ensures \result == celsius * 1.80 + 32.00;
    public double convertTemperature(double celsius) {
        return celsius * 1.80 + 32.00;
    }
}
