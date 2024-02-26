package cpe2c.cpe2cg2_g6;

public class NewtonRaphson implements RootFinder, OutputHelper{

    private final String expr;
    private final String fDer;
    private final String sDer;
    private double x;
    
    public NewtonRaphson(String expr, String fDer, String sDer, double initialGuess) {
        this.expr = expr;
        this.fDer = fDer;
        this.sDer = sDer;
        this.x = initialGuess;
    }

    private double fOfX() {
        return Parser.parse(this.expr, this.x);
    }

    private double fPrime() {
        return Parser.parse(this.fDer, this.x);
    }

    private double fDoublePrime() {
        return Parser.parse(this.sDer, this.x);
    }

    @Override 
    public double findRoot() {

        try {
            boolean notConvergent = (fOfX() * fDoublePrime()) >= Math.pow(fPrime(), 2);

            if (notConvergent) {
                throw new RuntimeException("the assumption is not convergent, choose another assumption...");
            }
            double old = this.x;
            for (int i = 0; i < 1000; i++) {

                double division = fOfX() / fPrime();
                double newX = this.x - division;
                
                //si output helper na ang gagalaw dito at magfefeed ng iterated values sa gui
                System.out.printf("x[%d]: %.6f\n", i, this.x);
                if (Math.abs((this.x-newX)/this.x) < 0.00001) {
                    break;
                }
                this.x = newX;
            }
            return this.x;
        } catch (RuntimeException e) {
            System.out.println("Error: " + e);
            return Double.NaN;
        }
    }
    
    @Override
    public String getOutput(){
        return String.format("Approximate root: %.6f", this.findRoot());
    }
}
