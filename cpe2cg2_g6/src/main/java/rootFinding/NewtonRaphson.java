package rootFinding;

import userIO.Parser;
import userIO.OutputHelper;
import userIO.MessageHelper;
import java.util.Stack;

public class NewtonRaphson implements RootFinder, OutputHelper {

    private final String expr;
    private final String fDer;
    private final String sDer;
    
    private double x;
    private final double threshold;
    private final Stack<Double> xValueHistory;
    
    private double fOfX() {
        return Parser.parse(this.expr, this.x);
    }

    private double fPrime() {
        return Parser.parse(this.fDer, this.x);
    }

    private double fDoublePrime() {
        return Parser.parse(this.sDer, this.x);
    }
    
    private double error(double current, double previous) {
        return Math.abs((current - previous) / current);
    }
    
    public NewtonRaphson(String expr, String fDer, String sDer, double initialGuess) {
        this.expr = expr;
        this.fDer = fDer;
        this.sDer = sDer;
        this.x = initialGuess;
        this.threshold = 0.00001;
        this.xValueHistory = new Stack<>();
    }

    @Override
    public double findRoot() {

        try {
            boolean convergent = (fOfX() * fDoublePrime()) < Math.pow(fPrime(), 2);
            boolean haveSyntaxError = Double.isNaN(fOfX()) || Double.isNaN(fPrime()) || Double.isNaN(fDoublePrime());
            
            if (haveSyntaxError) {
                throw new RuntimeException("Syntax Error");
            } else if (!convergent) {
                throw new RuntimeException("the assumption is not convergent\nchoose another assumption...");
            } 
            
            if (xValueHistory.isEmpty()) {
                xValueHistory.add(this.x);
            }
            
            System.out.printf("x[%d]: %.10f\n", 0, this.x);
            for (short i = 1; i < 1000; i++) {
                this.x = this.x - (fOfX() / fPrime());
                //temporary implementation for demo purpose only
                System.out.printf("x[%d]: %.10f\n", i, this.x);
                if (error(this.x, this.xValueHistory.pop()) < this.threshold) {
                    break;
                }
                this.xValueHistory.push(this.x);
                //actual implementation
                MessageHelper.send(String.format("x[%d]: %.10f\n", i, this.x));
            }
            return this.x;
        } catch (RuntimeException e) {
            //temporary implementation
            System.out.println("Error: " + e);
            //actual implementation
            MessageHelper.send(e.getMessage());
        }
        return Double.NaN;
    }

    @Override
    public String getOutput() {
        return String.format("Approximate root: %.10f", this.findRoot());
    }
}
