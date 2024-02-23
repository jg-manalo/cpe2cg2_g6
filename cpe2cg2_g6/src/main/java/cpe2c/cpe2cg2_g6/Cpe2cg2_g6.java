package cpe2c.cpe2cg2_g6;

import org.mariuszgromada.math.mxparser.License;
public class Cpe2cg2_g6 {

    public static void main(String[] args) {
        License.iConfirmNonCommercialUse("confirm");
        
        //BisectionMethod bm = new BisectionMethod("x^3-x^2+2", -2, 2);
        //NewtonRaphson nr = new NewtonRaphson("3x+sin(x)-e^x", "3+cos(x)-e^x", "-sin(x)-e^x", 0);
        NewtonRaphson nr = new NewtonRaphson("x^3-x^2+2", "3x^2-2x", "3x^2-2", -2);
        /*
        MOSS moss = new MOSS("3x+sin(x)-e^x", "(e^x-sin(x))/3", "(e^x-cos(x))/3" ,1);
        */
        System.out.println(nr.getOutput());
    }
}
