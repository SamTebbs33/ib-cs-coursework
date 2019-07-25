package zetta.lang.method.bool;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenBooleanExpression;
import zetta.token.TokenMethodCall;
import zetta.token.TokenVariable;

/**
 * 
 * @author samueltebbs, 16:59:26 - 4 Sep 2014
 */
public class MethodBooleanNot extends MethodBoolean {

    public MethodBooleanNot(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtBoolean.name } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns true if the argument is false and returns false otherwise" };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 1)) {
	    if ((args[0] instanceof TokenBooleanExpression)
		    || (args[0] instanceof TokenMethodCall)
		    || (args[0] instanceof TokenVariable)) return true;
	    else {
		new UnexpectedTokenException(args[0],
			Zetta.tokenBooleanExpression, Zetta.tokenMethodCall,
			Zetta.tokenVariable);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtBoolean, !Token.getBool(args[0]));
    }

}
