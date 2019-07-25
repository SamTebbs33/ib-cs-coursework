package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.exception.MathematicalException;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * Zetta
 * 
 * @author samueltebbs, 18:19:25
 */
public class MethodMathsDivide extends MethodMathsHypot {

    public MethodMathsDivide(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns argument1 / argument2. Generally faster than using expressions" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	double d;
	if ((d = Token.getDouble(args[1])) == 0) {
	    new MathematicalException("Divide by zero");
	}
	return asVariable(Zetta.dtNumber, Token.getDouble(args[0]) / d);
    }

}
