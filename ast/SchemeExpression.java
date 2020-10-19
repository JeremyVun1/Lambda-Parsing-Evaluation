package ast;

import java.util.Hashtable;
import java.util.Set;

public abstract class SchemeExpression {
    public abstract Set<Variable> freeNames();

    public abstract SchemeExpression substitute(Variable x, SchemeExpression e);

    // head normal form applicative order reduction. Evalute in place before further reduction. Rightmost innermost
    public abstract SchemeExpression reduce(Hashtable<Variable, SchemeExpression> symTable);

    public abstract String toString();
}