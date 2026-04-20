class ConvertToKelvin {
    /*@ spec_public @*/
    public double convertTemperature(double celsius) {
        /*@ 
            requires \true;
            ensures \result == celsius + 273.15;
        @*/
        return celsius + 273.15;
    }
}
