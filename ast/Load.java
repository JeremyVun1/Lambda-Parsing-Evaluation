package ast;

import java.io.*;
import java.util.*;
import parser.*;

// This allows us to spin up another parser to evaluate the other code unit
public class Load extends SchemeExpression {
    private String unitName;

    public Load(String unitName) {
        if (!unitName.endsWith(".ssu")) {
            unitName += ".ssu";
        }

        this.unitName = unitName;
    }

    public Load(Token unitName) {
        this(unitName.image.substring(1, unitName.image.length() - 1));
    }

    public Set<Variable> freeNames() {
        return new HashSet<Variable>();
    }

    public SchemeExpression substitute(Variable v, SchemeExpression e) {
        return this;
    }

    public SchemeExpression reduce(Hashtable<Variable, SchemeExpression> symTable) {
        try {
            SimpleSchemeParser parser = new SimpleSchemeParser(new FileInputStream(unitName));
            ArrayList<SchemeExpression> expressions = parser.Parse();

            SchemeExpression result = null;
            for (SchemeExpression exp : expressions) {
                result = exp.reduce(symTable);
            }
            
            return result;
        }
        catch(ParseException e) {
            throw new RuntimeException(e.toString());
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append( "(load \"" );
        result.append(unitName);
        result.append( "\")" );

        return result.toString();
    }

    public String getUnitName() {
        return unitName;
    }
}
