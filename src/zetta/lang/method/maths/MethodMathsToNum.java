package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * Zetta
 * 
 * @author samueltebbs, 11:18:54 - 25 Jul 2014
 */
public class MethodMathsToNum extends MethodMathsAbs {

    public MethodMathsToNum(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the number form of the argument" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtNumber, Token.getDouble(args[0]));
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtNumber;
    }

}
