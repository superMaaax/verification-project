class ConvertTemperature {
    /*@ spec_public @*/ public double[] convertTemperature(double celsius) {
        // Precondition: celsius is a valid double value (no specific constraints given)
        // Postcondition: returns an array of two doubles: [kelvin, fahrenheit]
        //   where kelvin = celsius + 273.15 and fahrenheit = celsius * 1.80 + 32.00
        /*@ 
            requires \true; // No specific preconditions required
            ensures \result.length == 2;
            ensures \result[0] == celsius + 273.15;
            ensures \result[1] == celsius * 1.80 + 32.00;
        @*/
        return new double[]{celsius + 273.15, celsius * 1.80 + 32.00};
    }
}
