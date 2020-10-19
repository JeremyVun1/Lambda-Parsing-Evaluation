package ast;

import parser.Token;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class Constant extends SchemeExpression {
    private BigDecimal value;

    public Constant(String value) {
        this.value = new BigDecimal(value);
    }

    public Constant(Token value) {
        this(value.image);
    }

    public Set<Variable> freeNames() {
        return new HashSet<Variable>();
    }

    public SchemeExpression substitute(Variable x, SchemeExpression e) {
        return this;
    }

    public SchemeExpression reduce(Hashtable<Variable, SchemeExpression> symTable) {
        return this;
    }

    public String toString() {
        return this.value.toString();
    }

    public BigDecimal getValue() {
        return value;
    }
}