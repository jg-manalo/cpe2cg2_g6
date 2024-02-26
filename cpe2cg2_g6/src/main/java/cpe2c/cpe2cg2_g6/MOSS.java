package cpe2c.cpe2cg2_g6;

import java.util.Stack;

public class MOSS implements RootFinder, OutputHelper {

    //keep as much as possible yung lahat ng private
    private final String function;
    private final String fSimplfied;
    private final String fDer;
    private final double threshold;

    private double x;
    private Stack<Double> xValueHistory;

    private double g(double x) {
        return Parser.parse(this.fSimplfied, this.x);
    }

    private double gx() {
        return Math.abs(Parser.parse(this.fDer, this.x));
    }
    
    private double error(double current, double previous){
        return Math.abs((current - previous)/current);
    }
    
    @Override
    public double findRoot() {
        try {
            if (this.gx() >= 1) {
                throw new RuntimeException("Solution will not converge, find another guess...");
            } 
            
            if(xValueHistory.isEmpty()){
                xValueHistory.add(this.x);
            }
            
            System.out.printf("Threshold: %f\n", this.threshold);
            System.out.printf("x[%d]: %.7f\n", 0, this.x);
            
            for (short k = 1; k < 1000; k++) { 
                this.x = g(this.x);
                if (error(this.x, xValueHistory.peek()) < this.threshold) {
                    break;
                }
                System.out.printf("x[%d]: %.10f Error %.9f\n", k, this.x, error(this.x, xValueHistory.peek()));
                
                xValueHistory.add(this.x);
                
            }

            return xValueHistory.peek();
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            return Double.NaN;
        }
    }

    public MOSS(String function, String fSimplified,String fDer ,double xk) {
        this.xValueHistory = new Stack<>();
        this.function = function;
        this.fSimplfied = fSimplified;
        this.fDer = fDer;
        this.x = xk;
        this.threshold = 0.00001;
    }

    @Override
    public String getOutput() {
        return String.format("Approximate root: %.7f", this.findRoot());
    }
}
