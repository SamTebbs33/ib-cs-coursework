package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * Zetta
 * 
 * @author samueltebbs, 07:22:23
 */
public class MethodMathsLogB extends MethodMathsHypot {

    public MethodMathsLogB(final String name) {
	super(name);
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtNumber, Math.log10(Token.getDouble(args[0]))
		/ Math.log10(Token.getDouble(args[1])));
    }

}
