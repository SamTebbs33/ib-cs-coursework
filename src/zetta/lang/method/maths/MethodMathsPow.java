package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.lang.Token;
import zetta.lang.Variable;

public class MethodMathsPow extends MethodMathsHypot {

    public MethodMathsPow(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtNumber,
		Math.pow(Token.getDouble(args[0]), Token.getDouble(args[1])));
    }

}
