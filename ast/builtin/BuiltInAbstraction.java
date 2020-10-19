package ast.builtin;

import java.util.HashSet;
import java.util.Set;
import ast.Abstraction;
import ast.SchemeExpression;
import ast.Variable;

public abstract class BuiltInAbstraction extends Abstraction {
    public BuiltInAbstraction(BuiltIn body) {
        super((Variable)null, body);
    }

    public Variable getVariable() {
        return ((BuiltIn)getBody()).getVariable();
    }

    public Set<Variable> freeNames() {
        return new HashSet<Variable>();
    }

    public SchemeExpression substitute(Variable v, SchemeExpression e) {
        return this;
    }

    public String toString() {
        return getBody().toString();
    }
}