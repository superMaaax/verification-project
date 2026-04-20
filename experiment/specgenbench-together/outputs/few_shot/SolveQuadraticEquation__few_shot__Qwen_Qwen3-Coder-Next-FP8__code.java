import java.lang.Math;

public class SolveQuadraticEquation {
    //@ requires a != 0;
    //@ ensures delta >= 0 <==> (\exists double r1, r2; \result[0] == r1 && \result[1] == r2 && r1 != 0.0 && r2 == 0.0);
    //@ ensures delta < 0 <==> (\exists double real, imag; \result[0] == real && \result[1] == imag && imag != 0.0);
    //@ ensures delta >= 0 <==> (\exists double r; \result[0] == r && \result[1] == 0.0 && a*r*r + b*r + c == 0.0);
    //@ ensures delta < 0 <==> (\exists double real, imag; \result[0] == real && \result[1] == imag && imag != 0.0 && a*(real+imag*I)*(real+imag*I) + b*(real+imag*I) + c == 0.0);
    public static double[] solve(int a, int b, int c) {
        double[] res = new double[2];
        double delta = b * b - 4 * a * c;
        if(delta >= 0) {
            res[0] = (-b - Math.sqrt(delta)) / (2 * a);
            res[1] = 0;
        }
        else {
            res[0] = -b / (2*a);
            res[1] = Math.sqrt(-delta) / (2*a);
        }
        return res;
    } 
}
