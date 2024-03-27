package rootFinding;

import rootFinding.RootFinder;
import userIO.Parser;
import userIO.OutputHelper;
import userIO.MessageHelper;

public class BisectionMethod implements RootFinder, OutputHelper {

    private final String expr;
    private double a;
    private double b;
    private double c;
    private final double threshold;

    private double midpoint(double a, double b) {
        return (a + b) / 2;
    }

    private double fA(double a) {
        return Parser.parse(this.expr, a);
    }

    private double fB(double b) {
        return Parser.parse(this.expr, b);
    }

    private double fC() {
        return Parser.parse(this.expr, c);
    }

    public BisectionMethod(String function, double a, double b) {
        this.expr = function;
        this.threshold = 0.01;
        this.a = a;
        this.b = b;
    }

    @Override
    public double findRoot() {

        try {
            boolean isAssumptionWrong = !(fA(this.a) * fB(this.b) < 0);
            boolean haveSyntaxError = Double.isNaN(fA(this.a)) || Double.isNaN(fB(this.b)) || Double.isNaN(fC());
            
            if (haveSyntaxError){
                throw new RuntimeException("Syntax Error");
            }
            else if (isAssumptionWrong) {
                throw new RuntimeException("Try a different guess: the assumptions cannot make it through");
            }
            for (short i = 1; i < 1000; i++) {
                this.c = midpoint(this.a, this.b);
                boolean atBestCase = fC() == 0.0;
                boolean isBelowTolerance = Math.abs(this.a - this.b) < this.threshold;
                
                //actual implementation
                MessageHelper.send(String.format("i[%d] a: %.10f b: %.10f c: %.10f f(c) %f\n", i, this.a, this.b, this.c, this.fC()));
                // temporary implementation
                System.out.printf("i[%d] a: %.10f b: %.10f c: %.10f f(c) %f\n", i, this.a, this.b, this.c, this.fC());
                if (atBestCase || isBelowTolerance) {
                    break;
                } else if (fC() < 0) {
                    this.a = this.c;
                } else {
                    this.b = this.c;
                }
            }
            return this.c;
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            return Double.NaN;
        }
    }

    @Override
    public String getOutput() {
        return String.format("Approximate root: %.10f", this.findRoot());
    }
}
