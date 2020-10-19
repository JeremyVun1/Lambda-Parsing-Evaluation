package ast;

import java.util.Hashtable;
import java.util.Set;
import java.lang.RuntimeException;
import java.math.BigDecimal;

// conditionExp must evaluate to a boolean

public class IfThenElse extends SchemeExpression {
    private SchemeExpression conditionExp;
    private SchemeExpression thenExp;
    private SchemeExpression elseExp;

    public IfThenElse(
        SchemeExpression conditionExp,
        SchemeExpression thenExp,
        SchemeExpression elseExp
    ) {
        this.conditionExp = conditionExp;
        this.thenExp = thenExp;
        this.elseExp = elseExp;
    }

    public Set<Variable> freeNames() {
        Set<Variable> result = conditionExp.freeNames();

        result.addAll(thenExp.freeNames());
        result.addAll(elseExp.freeNames());

        return result;
    }

    public SchemeExpression substitute(Variable v, SchemeExpression e) {
        return new IfThenElse(
            conditionExp.substitute(v, e),
            thenExp.substitute(v, e),
            elseExp.substitute(v, e)
        );
    }

    public SchemeExpression reduce(Hashtable<Variable, SchemeExpression> symTable) {
        SchemeExpression reducedExp = conditionExp.reduce(symTable);
        if (!(reducedExp instanceof Constant))
            throw new RuntimeException("Condition could not be reduced to a constant");

        BigDecimal condValue = ((Constant)reducedExp).getValue();
        BigDecimal falseValue = new BigDecimal(0.0);

        // compareTo returns 0 when BigDecimals match
        if (condValue.compareTo(falseValue) != 0) {
            return thenExp.reduce(symTable);
        }
        else return elseExp.reduce(symTable);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("(if ");
        result.append(conditionExp.toString());
        result.append(" ");
        result.append(thenExp.toString());
        result.append(" ");
        result.append(elseExp.toString());
        result.append(")");
        
        return result.toString();
    }

    public SchemeExpression getCondition() {
        return conditionExp;
    }

    public SchemeExpression getThen() {
        return thenExp;
    }

    public SchemeExpression getElse() {
        return elseExp;
    }
}