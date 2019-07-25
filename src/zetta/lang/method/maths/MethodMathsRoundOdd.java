package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.lang.Token;
import zetta.lang.Variable;

public class MethodMathsRoundOdd extends MethodMathsAbs {

    public MethodMathsRoundOdd(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Rounds the argument to the closest odd integer" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(
		Zetta.dtNumber,
		(Math.floor(Token.getDouble(args[0])) % 2) != 0 ? Math
			.floor(Token.getDouble(args[0])) : Math.ceil(Token
			.getDouble(args[0])));
    }

}
