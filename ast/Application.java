package ast;

import java.util.Hashtable;
import java.util.Set;

import ast.SchemeExpression;
import ast.Abstraction;

public class Application extends SchemeExpression {
    private SchemeExpression func;
    private SchemeExpression arg;

    public Application(SchemeExpression func, SchemeExpression arg) {
        this.func = func;
        this.arg = arg;
    }

    public Set<Variable> freeNames() {
        Set<Variable> result = func.freeNames();
        result.addAll(arg.freeNames());

        return result;
    }

    public SchemeExpression substitute(Variable v, SchemeExpression e) {
        return new Application(
            getFunction().substitute(v, e),
            getArgument().substitute(v, e)
        );
    }

    public SchemeExpression reduce(Hashtable<Variable, SchemeExpression> symTable) {
        SchemeExpression funcReduced = func.reduce(symTable);
        SchemeExpression argReduced = arg.reduce(symTable);

        if (funcReduced instanceof Abstraction) {
            SchemeExpression result = ((Abstraction)funcReduced)
                .getBody()
                .substitute(((Abstraction)funcReduced).getVariable(), argReduced)
                .reduce(symTable);

            return result;
        }
        else return this;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("(");
        result.append(func.toString());
        result.append(" ");
        result.append(arg.toString());
        result.append(")");

        return result.toString();
    }

    public SchemeExpression getArgument() {
        return arg;
    }

    public SchemeExpression getFunction() {
        return func;
    }
}