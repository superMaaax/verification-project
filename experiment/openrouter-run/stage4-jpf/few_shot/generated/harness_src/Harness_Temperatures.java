import gov.nasa.jpf.vm.Verify;

public class Harness_Temperatures {
  public static void main(String[] args) throws Exception {
    double celsius = (double) gov.nasa.jpf.vm.Verify.getInt(-2, 2);
    double[] __r = new Temperatures().convertTemperature(celsius);
  }
}
