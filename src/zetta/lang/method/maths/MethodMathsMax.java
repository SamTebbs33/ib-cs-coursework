package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.lang.Token;
import zetta.lang.Variable;

public class MethodMathsMax extends MethodMathsHypot {

    public MethodMathsMax(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the largest of the two arguments" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtNumber,
		Math.max(Token.getDouble(args[0]), Token.getDouble(args[1])));
    }

}
