package linearalgebra;

import java.util.Stack;
import java.util.Arrays;
import java.util.Scanner;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.JacobiPreconditioner;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class Jacobi {

    private final double[][] lhsMat;
    private final double[] rhsVector;
    private final double[] unknowns;
    private final int matSize;
    private final double tolerance;
    private final short iterationLiimit;

    public Jacobi() {
        this.tolerance = 0.00001;
        this.iterationLiimit = 1000;
        Scanner gets = new Scanner(System.in);
        System.out.print("Number of unknowns: ");
        this.matSize = gets.nextInt();
        this.lhsMat = loadLHSMat(this.matSize);
        this.rhsVector = loadRHSVec(this.matSize);
        this.unknowns = loadUnknowns(this.matSize);
    }

    public void showResult() {

        try {
            if (!isDiagonallyDominant(this.lhsMat)) {
                throw new RuntimeException("The LHS Matrix is not diagonally dominant. Jacobi Method would not work...");
            }
            
            RealVector b = new ArrayRealVector(this.rhsVector);
            RealVector x = new ArrayRealVector(this.unknowns);
            Stack<RealVector> xHistory = new Stack<>();
            JacobiPreconditioner D_inversed = new JacobiPreconditioner(getDiagonals(this.lhsMat), true);
            RealMatrix N = new Array2DRowRealMatrix(nonDiagonals(this.lhsMat));

            short i = 1;
            boolean isIterating = true;
            
            if (xHistory.isEmpty()) {
                xHistory.push(x);
            }

            System.out.printf("[%d] ", 0);
            for (int l = 0; l < this.matSize; l++) {
                System.out.printf("%.10f ", x.getEntry(l));
            }
            System.out.println("");
            
            while (isIterating) {
                RealVector prod = N.operate(xHistory.peek());
                RealVector ans = D_inversed.operate(b.subtract(prod));

                System.out.printf("[%d] ", i);
                if (i <= 1000 && idealErrorReached(ans, xHistory.pop())) {
                    isIterating = false;
                }

                for (int j = 0; j < x.getDimension(); j++) {
                    double newX = ans.getEntry(j);
                    x.setEntry(j, newX);
                    System.out.printf("%.10f ", x.getEntry(j));
                }
                xHistory.push(ans);

                i += 1;
                System.out.println("");
            }
        } catch (RuntimeException e) {
            System.out.println("Error: " + e);
        }
    }

    private boolean isDiagonallyDominant(double[][] lhsMat) {
        double sum = 0;
        for (int i = 0; i < this.matSize; i++) {
            for (int j = 0; j < this.matSize; j++) {
                if (i == j && sum > lhsMat[i][j]) {
                    return false;
                }
                sum += Math.abs(lhsMat[i][j]);
            }
            sum = 0.0;
        }
        return true;
    }

    private double[][] loadLHSMat(int size) {
        Scanner gets = new Scanner(System.in);
        double[][] leftMat = new double[size][size];
        for (int i = 0; i < matSize; i++) {
            for (int j = 0; j < matSize; j++) {
                System.out.printf("A[%d][%d]: ", i + 1, j + 1);
                leftMat[i][j] = gets.nextDouble();
            }
        }
        return leftMat;
    }

    private double[] loadRHSVec(int size) {
        Scanner gets = new Scanner(System.in);
        double[] rhsVec = new double[size];
        for (int i = 0; i < matSize; i++) {
            System.out.printf("{b}[%d]: ", i + 1);
            rhsVec[i] = gets.nextDouble();
        }
        return rhsVec;
    }

    private double[] loadUnknowns(int size) {
        Scanner gets = new Scanner(System.in);
        System.out.printf("Assumption: ");
        double[] guess = new double[size];
        double assumption = gets.nextDouble();
        for (int i = 0; i < matSize; i++) {
            guess[i] = assumption;
        }
        return guess;
    }

    private boolean idealErrorReached(RealVector current, RealVector previous) {
        for (int i = 0; i < this.matSize; i++) {

            double diff = Math.abs(current.getEntry(i) - previous.getEntry(i));
            double e = diff / current.getEntry(i);

            if (e >= this.tolerance) {
                return false;
            }
        }
        return true;
    }

    private double[] getDiagonals(double[][] rawMat) {
        double[] vec = new double[rawMat.length];
        for (int i = 0; i < rawMat.length; i++) {
            for (int j = 0; j < rawMat.length; j++) {
                if (i == j) {
                    vec[i] = rawMat[i][j];
                }
            }
        }
        return vec;
    }

    private double[][] nonDiagonals(double[][] rawMat) {
        double[][] N = Arrays.copyOfRange(rawMat, 0, rawMat.length);
        for (int i = 0; i < rawMat.length; i++) {
            for (int j = 0; j < rawMat.length; j++) {
                if (i == j) {
                    N[i][j] = 0.0;
                }
            }
        }
        return N;
    }
}
