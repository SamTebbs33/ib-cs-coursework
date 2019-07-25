package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.lang.Token;
import zetta.lang.Variable;

public class MethodMathsAtan extends MethodMathsAbs {

    public MethodMathsAtan(final String name) {
	super(name);
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtNumber, Math.atan(Token.getDouble(args[0])));
    }

}
