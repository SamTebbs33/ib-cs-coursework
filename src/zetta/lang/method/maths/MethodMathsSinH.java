package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.lang.Token;
import zetta.lang.Variable;

public class MethodMathsSinH extends MethodMathsAbs {

    public MethodMathsSinH(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtNumber, Math.sinh(Token.getDouble(args[0])));
    }

}
