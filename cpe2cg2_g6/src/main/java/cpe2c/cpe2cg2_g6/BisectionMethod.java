
package cpe2c.cpe2cg2_g6;

public class BisectionMethod{

    private double c;
    private String expr;
    private int depth;

    private double midpoint(double a, double b) {
        return (a + b) / 2;
    }

    private double fA(double a){
        return Parser.parse(this.expr, a);
    }
    
    private double fB(double b){
        return Parser.parse(this.expr, b);
    }
    
    private double fC(double c){
        return Parser.parse(this.expr, c);
    }
    

    public BisectionMethod(String expr) {
        this.expr = expr;
        this.depth = 1;
    }
    
    public double findRoot(double a, double b){
        this.c = midpoint(a, b);

        try {
            boolean isBelowTolerance = Math.abs(a - b) < 0.00001;
            boolean atBestCase = fC(this.c) == 0.0;
            boolean atMaxIteration = this.depth == 999;
            boolean isAssumptionWrong = !(fA(a) * fB(b) < 0);
            System.out.printf("step: %d a: %f b: %f c: %f f(c) %f\n", depth, a, b, c, this.fC(c));
            
            if (isAssumptionWrong){
                throw new RuntimeException("Try a different guess: the assumptions cannot make it through");
            }
            if (isBelowTolerance || atBestCase || atMaxIteration) {
                return c;
            } else if (fA(a) * fC(c) < 0) {
                depth++;
                return findRoot(a, this.c);
            } else {
                depth++;
                return findRoot(b, this.c);
            }
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            return Double.NaN;
        }
    }
}