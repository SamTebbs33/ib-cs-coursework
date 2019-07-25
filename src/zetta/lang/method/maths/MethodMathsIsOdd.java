package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;

public class MethodMathsIsOdd extends MethodMathsAbs {

    public MethodMathsIsOdd(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Rounds true if the argument is odd" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtBoolean, (Token.getDouble(args[0]) % 2) != 0);
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtBoolean;
    }

}
