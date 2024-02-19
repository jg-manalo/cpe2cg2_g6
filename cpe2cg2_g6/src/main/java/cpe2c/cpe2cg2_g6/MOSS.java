package cpe2c.cpe2cg2_g6;

import java.util.Scanner;

public class MOSS implements UserIO {

    //keep as much as possible yung lahat ng private
    private String function;
    private String fDer;
    //yung nasa baba is yung xk
    private double x;
    private final double threshold;

    private double fOfX() {
        return Parser.parse(this.function, x);
    }

    //eto yung g na tinutukoy sa lecture
    private double fPrime() {
        return Parser.parse(this.fDer, this.x);
    }

    private double gx() {
        return Math.abs(fPrime());
    }

    private double findRoot() {
        try {
            if (this.gx() >= 1) {
                throw new RuntimeException("Solution will not converge, find another guess...");
            } 
            
            System.out.printf("x[%d]: %f\n", 0, this.x);
            for (short k = 1; k < 1000; k++) { 
                double previous = this.x;
                this.x = fPrime();
                
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

    public MOSS() {
        this.threshold = 0.00001;
    }

    //kahit dito mo ilagay yung pag input ni user ng f(x) at ng assumption
    @Override
    public void getInput() {
        //dunno lang kung require pa kunin yung f(x) but f'(x) is required
        try (Scanner gets = new Scanner(System.in)) {
            //dunno lang kung require pa kunin yung f(x) but f'(x) is required
            System.out.println("(Optional)[Type and enter] simplified function in standard form: ");
            this.function = gets.next();
            
            System.out.println("[Type and enter] the first derivative: ");
            this.fDer = gets.next();
            
            System.out.println("[Type and enter] the assumption: ");
            this.x = gets.nextDouble();
        } catch (RuntimeException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void displayOutput() {
        System.out.printf("root: %.7f", this.findRoot());
    }
}
