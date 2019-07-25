package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Token;
import zetta.lang.Variable;

public class MethodMathsFloor extends MethodMathsAbs {

    public MethodMathsFloor(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 1)) {
	    if (Token.tokenCanBeInteger(args[0])) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenInteger,
			Zetta.tokenMethodCall, Zetta.tokenVariable,
			Zetta.tokenExpression);
	    }
	}
	return false;
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Rounds the number down. Negative numbers are rounded towards 0" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtInteger,
		Math.floor(Token.getInteger(args[0])));
    }

}
