class Temperatures {
    /*@ spec_public @*/ public double[] convertTemperature(double celsius) {
        // Precondition: none explicitly required, but celsius should be a valid double
        // Postcondition: returns an array of three elements:
        //   [0] = original Celsius value,
        //   [1] = Celsius converted to Kelvin (Celsius + 273.15),
        //   [2] = Celsius converted to Fahrenheit (Celsius * 1.80 + 32.00)
        /*@ 
            requires \true;
            ensures \result.length == 3;
            ensures \result[0] == celsius;
            ensures \result[1] == celsius + 273.15;
            ensures \result[2] == celsius * 1.80 + 32.00;
        @*/
        return new double[]{celsius, celsius + 273.15, celsius * 1.80 + 32.00};
    }
}
