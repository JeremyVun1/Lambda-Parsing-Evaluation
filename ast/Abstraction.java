package ast;

import java.util.Hashtable;
import java.util.Set;
import parser.Token;

public class Abstraction extends SchemeExpression {
    private Variable variable;
    private SchemeExpression body;

    public Abstraction(Variable variable, SchemeExpression body) {
        this.variable = variable;
        this.body = body;
    }

    public Abstraction(Token variable, SchemeExpression body) {
        this(new Variable(variable), body);
    }

    public Set<Variable> freeNames() {
        Set<Variable> result = body.freeNames();
        result.remove(getVariable());

        return result;
    }

    public SchemeExpression substitute(Variable variable, SchemeExpression e) {
        if (getVariable().equals(variable)) {
            return this;
        }
        else {
            Set<Variable> frees = e.freeNames();
            Variable v = getVariable();

            if (frees.contains(v)) {
                frees.addAll(getBody().freeNames());
                Variable freshV = v.getFreshVariable(frees);

                SchemeExpression newBody = body.substitute(v, freshV);
                newBody = newBody.substitute(variable, e);
                return new Abstraction(freshV, newBody);
            }
            else {
                return new Abstraction(v, body.substitute(variable, e));
            }
        }
    }

    public SchemeExpression reduce(Hashtable<Variable, SchemeExpression> symTable) {
        return this;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("(lambda ");
        result.append(variable.toString());
        result.append(".");
        result.append(body.toString());
        result.append(")");

        return result.toString();
    }

    public SchemeExpression getBody() {
        return body;
    }

    public Variable getVariable() {
        return variable;
    }
}