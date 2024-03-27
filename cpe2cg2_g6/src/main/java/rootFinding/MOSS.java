package rootFinding;

import rootFinding.RootFinder;
import userIO.Parser;
import userIO.OutputHelper;
import java.util.Stack;

public class MOSS implements RootFinder, OutputHelper {

    //keep as much as possible yung lahat ng private
    private final String function;
    private final String fSimplified;
    private final String fDer;
    private final double threshold;

    private double x;
    private Stack<Double> xValueHistory;

    private double g(double x) {
        return Parser.parse(this.fSimplified, x);
    }

    private double gx() {
        return Parser.parse(this.fDer, this.x);
    }

    private double error(double current, double previous) {
        return Math.abs((current - previous) / current);
    }

    @Override
    public double findRoot() {
        try {
            boolean notConvergent = this.gx() >= 1;
            boolean haveSyntaxError = Double.isNaN(g(this.x)) || Double.isNaN(gx());

            if (haveSyntaxError) {
                throw new RuntimeException("Syntax Error");
            } else if (notConvergent) {
                throw new RuntimeException("Solution will not converge, find another guess...");
            }

            if (xValueHistory.isEmpty()) {
                xValueHistory.add(this.x);
            }

            System.out.printf("Threshold: %.5f\n", this.threshold);
            System.out.printf("x[%d]: %.10f\n", 0, this.x);

            for (short k = 1; k < 1000; k++) {
                this.x = g(this.x);
                if (error(this.x, xValueHistory.peek()) < this.threshold) {
                    break;
                }
                System.out.printf("x[%d]: %.9f Error %.10f\n", k, this.x, error(this.x, xValueHistory.peek()));

                xValueHistory.push(this.x);

            }

            return xValueHistory.peek();
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            return Double.NaN;
        }
    }

    public MOSS(String function, String fSimplified, String fDer, double xk) {
        this.xValueHistory = new Stack<>();
        this.function = function;
        this.fSimplified = fSimplified;
        this.fDer = fDer;
        this.x = xk;
        this.threshold = 0.00001;
    }

    @Override
    public String getOutput() {
        return String.format("Approximate root: %.10f", this.findRoot());
    }
}
