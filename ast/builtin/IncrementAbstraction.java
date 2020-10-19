package ast.builtin;

import java.math.BigDecimal;

import ast.Constant;
import ast.SchemeExpression;
import ast.Variable;

public class IncrementAbstraction extends BuiltInAbstraction {
    // built in is encapsulated in the corresponding built in abstraction
    private static class Increment extends BuiltIn {
        public Increment(Variable variable) {
            super(variable);
        }

        public SchemeExpression substitute(Variable x, SchemeExpression e) {
            if (getVariable().equals(x)) {
                if (e instanceof Constant) {
                    BigDecimal number = ((Constant)e).getValue().add(new BigDecimal(1.0));
                    return new Constant(number.toString());
                }
                else {
                    throw new ArithmeticException("Illegal argument: " + e);
                }
            }
            else return this;
        }

        public String toString() {
            StringBuilder result = new StringBuilder();

            result.append("incr(");
            result.append(getVariable().toString());
            result.append(")");

            return result.toString();
        }
    }

    public IncrementAbstraction() {
        super(new Increment(new Variable("x")));
    }
}