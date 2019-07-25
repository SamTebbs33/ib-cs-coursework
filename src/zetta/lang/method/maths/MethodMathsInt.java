package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * Zetta
 * 
 * @author samueltebbs, 18:28:41
 */
public class MethodMathsInt extends MethodMathsAbs {

    public MethodMathsInt(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the argument in inetger form with the decimal part omitted" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtNumber, (int) Token.getDouble(args[0]));
    }

}
