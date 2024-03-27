package cpe2c.cpe2cg2_g6;

import rootFinding.NewtonRaphson;
import rootFinding.MOSS;
import rootFinding.BisectionMethod;
import linearalgebra.Jacobi;
import org.mariuszgromada.math.mxparser.License;
import java.util.Scanner;

public class Cpe2cg2_g6 {

    public static void main(String[] args) {
        License.iConfirmNonCommercialUse("confirm");

        Scanner input = new Scanner(System.in);
        System.out.println("Group 6's Numerical Methods Calculator");
        System.out.println("[Type and Enter]:\n[B]isection Method\n[M]OSS\n[N]ewton Raphson");
        char mode = input.next().charAt(0);

        switch (mode) {
            case 'B' -> {
                System.out.println("[Type and Enter] Function:");
                String function = input.next();
                System.out.println("[Type and Enter] Value for a approximation:");
                double a = input.nextDouble();
                System.out.println("[Type and Enter] Value for b approximation:");
                double b = input.nextDouble();

                BisectionMethod bm = new BisectionMethod(function, a, b);
                System.out.println(bm.getOutput());
            }

            case 'M' -> {
                System.out.println("[Type and Enter] Function:");
                String func = input.next();
                System.out.println("[Type and Enter] Simplified Function:");
                String fSimplified = input.next();
                System.out.println("[Type and Enter] First Derivative of the Sinmplified Function:");
                String fDer = input.next();
                System.out.println("[Type and Enter] Approximate Value:");
                double x = input.nextDouble();

                MOSS moss = new MOSS(func, fSimplified, fDer, x);
                System.out.println(moss.getOutput());
            }

            case 'N' -> {
                System.out.println("[Type and Enter] Function:");

                String nFunc = input.next();
                System.out.println("[Type and Enter] First Derivative of the Function:");

                String nFDer = input.next();
                System.out.println("[Type and Enter] Second Derivative of the Function:");

                String nSDer = input.next();
                System.out.println("[Type and Enter] Approximate Value:");

                double approximate = input.nextDouble();

                NewtonRaphson nr = new NewtonRaphson(nFunc, nFDer, nSDer, approximate);
                System.out.println(nr.getOutput());
            }
        }
//          Jacobi jacobi = new Jacobi();
//          jacobi.showResult();
    }
}
