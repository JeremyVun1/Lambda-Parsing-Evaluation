package ast;

import java.util.Hashtable;
import java.util.Set;
import parser.Token;

public class Define extends SchemeExpression {
    private Variable variable;
    private SchemeExpression exp;

    public Define(Variable variable, SchemeExpression exp) {
        this.variable = variable;
        this.exp = exp;
    }

    public Define(Token variable, SchemeExpression exp) {
        this(new Variable(variable.image), exp);
    }

    public Set<Variable> freeNames() {
        return exp.freeNames();
    }

    public SchemeExpression substitute(Variable v, SchemeExpression e) {
        return new Define(getVariable(), exp.substitute(v, e));
    }

    public SchemeExpression reduce(Hashtable<Variable, SchemeExpression> symTable) {
        SchemeExpression evaluated = exp.reduce(symTable);
        symTable.put(variable, evaluated);

        return evaluated;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("(define ");
        result.append(variable.toString());
        result.append(" ");
        result.append(exp.toString());
        result.append(")");

        return result.toString();
    }

    public SchemeExpression getExp() {
        return exp;
    }

    public Variable getVariable() {
        return variable;
    }
}