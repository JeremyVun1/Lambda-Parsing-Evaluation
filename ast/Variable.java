package ast;

import java.util.Hashtable;
import java.util.HashSet;
import java.util.Set;
import java.lang.RuntimeException;
import parser.Token;


public class Variable extends SchemeExpression {
    private String value;

    public Variable(String identifier) {
        value = identifier;
    }

    public Variable(Token identifier) {
        value = identifier.image;
    }

    public boolean equals(Object other) {
        if (other instanceof Variable) {
            return ((Variable)other).getValue().equals(value);
        }

        return false;
    }

    public int hashCode() {
        return value.hashCode();
    }

    public Set<Variable> freeNames() {
        Set<Variable> result = new HashSet<Variable>();
        result.add(this);

        return result;
    }

    // get a fresh variable that is not already in the set of free names
    public Variable getFreshVariable(Set<Variable> setOfFreeNames) {
        Variable fresh = new Variable(value + "%");
        while (setOfFreeNames.contains(fresh)) {
            fresh.value += "%";
        }

        return fresh;
    }

    public SchemeExpression substitute(Variable v, SchemeExpression e) {
        if (equals(v)) {
            // [e/x]x
            return e;
        }
        else {
            // [e/x]y
            return this;
        }
    }

    public SchemeExpression reduce(Hashtable<Variable, SchemeExpression> symTable) {
        if (symTable.containsKey(this)) {
            return symTable.get(this);
        }
        else throw new RuntimeException("Error: " + value + " has no meaning");
    }

    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }
}