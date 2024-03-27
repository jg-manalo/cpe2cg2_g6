package userIO;

import userIO.Input;
import org.mariuszgromada.math.mxparser.*;

public class Parser {

    public static double parse(String function, double variable) {
        if (Input.isInvalid(function)) {
            return Double.NaN;
        } else {
            Argument x = new Argument("x", variable);
            Expression data = new Expression(function, x);
            return data.calculate();
        }
    }
}
