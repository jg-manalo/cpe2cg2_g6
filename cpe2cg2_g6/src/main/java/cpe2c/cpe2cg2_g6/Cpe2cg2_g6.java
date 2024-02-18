package cpe2c.cpe2cg2_g6;

import org.mariuszgromada.math.mxparser.License;

public class Cpe2cg2_g6 {

    public static void main(String[] args) {
        License.iConfirmNonCommercialUse("confirm");
        String function = "x^3-4x+1";
        String fDer = "3x^2-4";
        String sDer = "6x";
        double x = 2;
        
        NewtonRaphson nr = new NewtonRaphson(function,fDer);
        System.out.printf("root: %.5f", nr.findRoot(x));
    }
}
