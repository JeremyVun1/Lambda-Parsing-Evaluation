package ast.builtin;

import java.math.BigDecimal;

import ast.Constant;
import ast.SchemeExpression;
import ast.Variable;

public class DecrementAbstraction extends BuiltInAbstraction {
    public DecrementAbstraction() {
        //create with variable name "x"
        super(new Decrement(new Variable("x")));
    }

    // built in is encapsulated in the corresponding built in abstraction
    private static class Decrement extends BuiltIn {
        public Decrement(Variable variable) {
            super(variable);
        }

        // substitute the variable with e where e is a constant
        public SchemeExpression substitute(Variable x, SchemeExpression e) {
            if (getVariable().equals(x)) {
                if (e instanceof Constant) {
                    BigDecimal number = ((Constant)e).getValue().subtract(new BigDecimal(1.0));
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

            result.append("decr(");
            result.append(getVariable().toString());
            result.append(")");

            return result.toString();
        }
    }
}