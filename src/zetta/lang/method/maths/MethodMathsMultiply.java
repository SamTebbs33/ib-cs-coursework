package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * Zetta
 * 
 * @author samueltebbs, 18:19:02
 */
public class MethodMathsMultiply extends MethodMathsHypot {

    public MethodMathsMultiply(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns argument1 * argument2. Generally faster than using expressions" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtNumber,
		Token.getDouble(args[0]) * Token.getDouble(args[1]));
    }

}
