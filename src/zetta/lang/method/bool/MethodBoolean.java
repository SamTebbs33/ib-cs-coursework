package zetta.lang.method.bool;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.method.MethodLibrary;
import zetta.token.TokenBooleanExpression;
import zetta.token.TokenMethodCall;
import zetta.token.TokenVariable;

/**
 * 
 * @author samueltebbs, 16:51:38 - 4 Sep 2014
 */
public class MethodBoolean extends MethodLibrary {

    public MethodBoolean(final String name) {
	super(name, Zetta.dtBoolean.name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtBoolean.name, Zetta.dtBoolean.name } };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtBoolean.name };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 2)) {
	    if ((args[0] instanceof TokenBooleanExpression)
		    || (args[0] instanceof TokenMethodCall)
		    || (args[0] instanceof TokenVariable)) {
		if ((args[1] instanceof TokenBooleanExpression)
			|| (args[1] instanceof TokenMethodCall)
			|| (args[1] instanceof TokenVariable)) return true;
		else {
		    new UnexpectedTokenException(args[1],
			    Zetta.tokenBooleanExpression,
			    Zetta.tokenMethodCall, Zetta.tokenVariable);
		}
	    } else {
		new UnexpectedTokenException(args[0],
			Zetta.tokenBooleanExpression, Zetta.tokenMethodCall,
			Zetta.tokenVariable);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtBoolean,
		bool(Token.getBool(args[0]), Token.getBool(args[1])));
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtBoolean;
    }

    protected boolean bool(final boolean b1, final boolean b2) {
	return false;
    }

}
