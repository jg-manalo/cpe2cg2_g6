package cpe2c.cpe2cg2_g6;

import java.util.Scanner;

public class MOSS implements RootFinder, OutputHelper {

    //keep as much as possible yung lahat ng private
    private String function;
    private String fSimplfied;
    private String fDer;
    //yung nasa baba is yung xk
    private double x;
    private final double threshold;

    //eto yung g na tinutukoy sa lecture
    private double g() {
        return Parser.parse(this.fSimplfied, this.x);
    }

    private double gx() {
        return Math.abs(Parser.parse(this.fDer, this.x));
    }
    
    @Override
    public double findRoot() {
        try {
            if (this.gx() >= 1) {
                throw new RuntimeException("Solution will not converge, find another guess...");
            } 
            
            System.out.printf("x[%d]: %f\n", 0, this.x);
            for (short k = 1; k < 1000; k++) { 
                double previous = this.x;
                this.x = g();
                
                double error = Math.abs((previous - this.x) / previous);
                System.out.printf("x[%d]: %.7f\n", k, this.x);
                if (error < this.threshold) {
                    break;
                }
            }

            return this.x;
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            return Double.NaN;
        }
    }

    public MOSS(String function, String fSimplified,String fDer ,double xk) {
        this.function = function;
        this.fSimplfied = fSimplified;
        this.fDer = fDer;
        this.x = xk;
        this.threshold = 0.00001;
    }

    @Override
    public String getOutput() {
        return String.format("root: %.7f", this.findRoot());
    }
}
