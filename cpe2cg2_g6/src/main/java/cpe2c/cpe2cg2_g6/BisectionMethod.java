package cpe2c.cpe2cg2_g6;

public class BisectionMethod implements RootFinder, OutputHelper {

    private String expr;
    private short depth;
    private double a;
    private double b;
    private double c;
    private double threshold;

    private double midpoint(double a, double b) {
        return (a + b) / 2;
    }

    private double fA(double a) {
        return Parser.parse(this.expr, this.a);
    }

    private double fB(double b) {
        return Parser.parse(this.expr, this.b);
    }

    private double fC() {
        return Parser.parse(this.expr, this.c);
    }

    public BisectionMethod(String function, double a, double b) {
        this.expr = function;
        this.threshold = 0.01;
        this.depth = 1;
        this.a = a;
        this.b = b;
    }

    @Override
    public double findRoot() {

        try {
            boolean isAssumptionWrong = !(fA(this.a) * fB(this.b) < 0);

            if (isAssumptionWrong) {
                throw new RuntimeException("Try a different guess: the assumptions cannot make it through");
            }
            for (short i = 1; i < 1000; i++) {
                this.c = midpoint(this.a, this.b);
                boolean atBestCase = fC() == 0.0;
                boolean isBelowTolerance = Math.abs(this.a - this.b) < this.threshold;
                System.out.printf("i[%d] a: %f b: %f c: %f f(c) %f\n", i, this.a, this.b, this.c, this.fC());
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
        return String.format("root: %.6f", this.findRoot());
    }
}
