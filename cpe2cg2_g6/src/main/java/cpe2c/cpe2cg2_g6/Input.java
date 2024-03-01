/*
Considered true:
1. If the format follows Coefficient if there is, Variable, then yada yada
2. If the parenthesis are balanced
3. No consecutive used of mathematical operators 
4. If ^ for exponents, must be preceeded by a variable or digits then followed by a variable or digits (testing phase)
 */
package cpe2c.cpe2cg2_g6;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {

    public static boolean wrongBasicFormat(String input) {
        Pattern pattern = Pattern.compile("^\\+|-?[a-z]?[\\d]?");
        Matcher matcher = pattern.matcher(input);

        return !matcher.find();
    }

    private static boolean imbalanceParentheses(String input) {
        Pattern pattern = Pattern.compile(("(?:[^()]*\\([^()]*\\))*"));
        Matcher matcher = pattern.matcher(input);

        return !matcher.find();
    }

    private static boolean successiveOperators(String input) {
        Pattern pattern = Pattern.compile("(?:[+\\-*\\/E])(?![+\\-*\\/E])");
        Matcher matcher = pattern.matcher(input);

        return !matcher.find();

    }

    private static boolean illegalExponential(String input) {
        Pattern pattern = Pattern.compile("[0-9]*[xe]{0,1}\\^[0-9a-z]");
        Matcher matcher = pattern.matcher(input);

        return !matcher.find();

    }

    public static boolean isInvalid(String input) {
        return imbalanceParentheses(input) || successiveOperators(input) || wrongBasicFormat(input) || illegalExponential(input);
    }
}
