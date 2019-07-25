package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.lang.Token;
import zetta.lang.Variable;

public class MethodMathsCosH extends MethodMathsAbs {

    public MethodMathsCosH(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtNumber, Math.cosh(Token.getDouble(args[0])));
    }

}
