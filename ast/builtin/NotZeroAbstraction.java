package ast.builtin;

import java.math.BigDecimal;

import ast.Constant;
import ast.SchemeExpression;
import ast.Variable;

public class NotZeroAbstraction extends BuiltInAbstraction {
    public NotZeroAbstraction() {
        super(new NotZero(new Variable("x")));
    }

    // built in is encapsulated in the corresponding built in abstraction
    // why???
    private static class NotZero extends BuiltIn {
        public NotZero(Variable variable) {
            super(variable);
        }

        public SchemeExpression substitute(Variable x, SchemeExpression e) {
            if (getVariable().equals(x)) {
                BigDecimal zero = new BigDecimal(0.0);

                if (((Constant)e).getValue().compareTo(zero) == 0) {
                    return new Constant(zero.toString());
                }
                else return new Constant("1.0");
            }
            else return this;
        }

        public String toString() {
            StringBuilder result = new StringBuilder();

            result.append("notzero(");
            result.append(getVariable().toString());
            result.append(")");

            return result.toString();
        }
    }
}