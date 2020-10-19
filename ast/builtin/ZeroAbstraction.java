package ast.builtin;

import java.math.BigDecimal;

import ast.Constant;
import ast.SchemeExpression;
import ast.Variable;

public class ZeroAbstraction extends BuiltInAbstraction {
    public ZeroAbstraction() {
        super(new Zero(new Variable("x")));
    }

    // built in is encapsulated in the corresponding built in abstraction
    // why???
    private static class Zero extends BuiltIn {
        public Zero(Variable variable) {
            super(variable);
        }

        public SchemeExpression substitute(Variable x, SchemeExpression e) {
            if (getVariable().equals(x)) {
                if (e instanceof Constant) {
                    BigDecimal zero = new BigDecimal(0.0);
                    if (((Constant)e).getValue().compareTo(zero) == 0) {
                        return new Constant("1.0");
                    }
                    else return new Constant(zero.toString());
                }
                else {
                    throw new ArithmeticException("Illegal argument: " + e);
                }
            }
            else return this;
        }

        public String toString() {
            StringBuilder result = new StringBuilder();

            result.append("isZero(");
            result.append(getVariable().toString());
            result.append(")");

            return result.toString();
        }
    }
}