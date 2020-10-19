package ast.builtin;

import java.util.*;
import ast.*;

public abstract class BuiltIn extends SchemeExpression {
    private Variable variable;

    public BuiltIn(Variable variable) {
        this.variable = variable;
    }

    public Set<Variable> freeNames() {
        return new HashSet<Variable>();
    }

    public SchemeExpression reduce(Hashtable<Variable, SchemeExpression> symTable) {
        return this;
    }

    public Variable getVariable() {
        return this.variable;
    }
}