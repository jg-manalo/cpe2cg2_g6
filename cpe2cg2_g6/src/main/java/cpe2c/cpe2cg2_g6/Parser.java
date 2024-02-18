package cpe2c.cpe2cg2_g6;

import org.mariuszgromada.math.mxparser.*;

public class Parser {

    public static double parse(String function, double variable) {

        Argument x = new Argument("x", variable);
        Expression data = new Expression(function, x);

        return data.calculate();
    }
}
