package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * Zetta
 * 
 * @author samueltebbs, 00:25:48
 */
public class MethodMathsLog2 extends MethodMathsAbs {

    public MethodMathsLog2(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the log to base 2 of the argument" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtNumber, Math.log10(Token.getDouble(args[0]))
		/ Math.log10(2));
    }

}
