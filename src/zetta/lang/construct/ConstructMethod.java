package zetta.lang.construct;

import zetta.Zetta;
import zetta.exception.MethodAlreadyDefinedException;
import zetta.exception.UnexpectedArgumentQuantityException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Construct;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenConstruct;
import zetta.token.TokenMethodArgument;
import zetta.token.TokenString;
import zetta.token.TokenUserMethodCall;
import zetta.token.TokenVariable;

/**
 * The method construct Format: method(name, arguments...){ statements... }
 * 
 * @author samueltebbs, 12:54:51 - 24 Jul 2014
 */
public class ConstructMethod extends Construct {

    public ConstructMethod(final String name) {
	super(name);
    }

    @Override
    public boolean canConstruct(final Token[] tokens, final TokenConstruct t) {
	if (tokens.length >= 1) {
	    if (tokens[0] instanceof TokenString) {
		for (int c = 1; c < tokens.length; c++) {
		    if (!(tokens[c] instanceof TokenMethodArgument)) {
			new UnexpectedTokenException(tokens[c],
				Zetta.tokenMethodArgument);
			return false;
		    }
		    Zetta.compVariables.put(tokens[c].value,
			    ((TokenMethodArgument) tokens[c]).dType.name);
		}
		final String name = tokens[0].value;
		for (final TokenConstruct token : Zetta.userMethods) {
		    if (token.subTokens[0].value.equals(name)) {
			new MethodAlreadyDefinedException(name, "");
		    }
		}
		Zetta.userMethods.add(t);
		return true;
	    } else {
		new UnexpectedTokenException(tokens[0], Zetta.tokenString);
	    }
	} else {
	    new UnexpectedArgumentQuantityException(tokens.length, 1);
	}
	return false;
    }

    // userMethod contains the body and required arguments, caller contains the
    // arguments being sent to method
    public static Variable run(final TokenConstruct userMethod,
	    final TokenUserMethodCall caller) {
	if (caller.subTokens.length == (userMethod.subTokens.length - 1)) {
	    for (int c = 0; c < caller.subTokens.length; c++) {
		final Token t = caller.subTokens[c];
		final TokenMethodArgument tma = (TokenMethodArgument) userMethod.subTokens[c + 1];
		if (t instanceof TokenVariable) {
		    final Variable v = Zetta.getVariableOfType(t);
		    final Variable v2 = new Variable(v.dType, v.name, "");
		    v.dType.setVariableToVariable(v2, v);
		    v2.name = tma.value;
		    userMethod.localVariables.add(v2);
		} else {
		    userMethod.localVariables.add(tma.dType.initVariable(
			    tma.value, new Token[] { t }));
		}
	    }
	    Zetta.runFromConstruct(userMethod.body, userMethod, true);
	} else {
	    new UnexpectedArgumentQuantityException(caller.subTokens.length,
		    userMethod.subTokens.length);
	}
	return null;
    }

}
