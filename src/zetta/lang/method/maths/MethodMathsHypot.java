package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Token;
import zetta.lang.Variable;

public class MethodMathsHypot extends MethodMaths {

    public MethodMathsHypot(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtNumber.name, Zetta.dtNumber.name } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the square root of (argument1^2 + argument^2), according to pythagoras' theorem." };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 2)) {
	    if (Token.tokenCanBeNumber(args[0])) {
		if (Token.tokenCanBeNumber(args[1])) return true;
		else {
		    new UnexpectedTokenException(args[1], Zetta.tokenNumber,
			    Zetta.tokenMethodCall, Zetta.tokenVariable,
			    Zetta.tokenExpression);
		}
	    } else {
		new UnexpectedTokenException(args[0], Zetta.tokenNumber,
			Zetta.tokenMethodCall, Zetta.tokenVariable,
			Zetta.tokenExpression);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtNumber,
		Math.hypot(Token.getDouble(args[0]), Token.getDouble(args[1])));
    }

}
