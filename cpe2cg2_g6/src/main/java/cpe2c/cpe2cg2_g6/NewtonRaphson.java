/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cpe2c.cpe2cg2_g6;

/**
 *
 * @author PC 2
 */
public class NewtonRaphson {

    private String expr;
    private String fDer;
    private String sDer;

    public NewtonRaphson(String expr, String fDer) {
        this.expr = expr;
        this.fDer = fDer;
        this.sDer = sDer;
    }

    private double fOfX(double x) {
        return Parser.parse(this.expr, x);
    }

    private double fPrime(double x) {
        return Parser.parse(this.fDer, x);
    }

    private double fDoublePrime(double x) {
        return Parser.parse(this.sDer, x);
    }

    public double findRoot(double x) {

        try {
            boolean notConvergent = (fOfX(x) * fDoublePrime(x) > Math.pow(fPrime(x), 2));

            if (notConvergent) {
                throw new RuntimeException("the assumption is not convergent, choose another assumption...");
            }
            double old = x;
            for (int i = 1; i < 1000; i++) {

                double division = fOfX(x) / fPrime(x);
                double newX = x - division;
                System.out.printf("x: %.6f\n", x);
                if (Math.abs(division) < 0.00001) {
                    break;
                }
                x = newX;
            }
            return x;
        } catch (RuntimeException e) {
            System.out.println("Error: " + e);
            return Double.NaN;
        }
    }
}
