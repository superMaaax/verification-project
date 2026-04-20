import java.lang.Math;

public class SolveQuadraticEquation {
    /*@ spec_public @*/
    public static double[] solve(int a, int b, int c) {
        // Precondition: a must be non-zero to ensure the equation is quadratic
        requires a != 0;
        
        double[] res = new double[2];
        double delta = b * b - 4 * a * c;
        
        if(delta >= 0) {
            // Loop not applicable here, but we can specify behavior for this branch
            ensures res[1] == 0;
            ensures res[0] == (-b - Math.sqrt(delta)) / (2.0 * a);
        }
        else {
            // Loop not applicable here, but we can specify behavior for this branch
            ensures res[1] == Math.sqrt(-delta) / (2.0 * a);
            ensures res[0] == -b / (2.0 * a);
        }
        
        ensures res.length == 2;
        ensures (\forall int i; 0 <= i && i < 2; Double.isFinite(res[i]) || Double.isInfinite(res[i]));
        
        res[0] = (-b - Math.sqrt(delta)) / (2 * a);
        res[1] = 0;
        
        if(delta < 0) {
            res[0] = -b / (2*a);
            res[1] = Math.sqrt(-delta) / (2*a);
        }
        
        return res;
    } 
}
