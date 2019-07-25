package zetta.lang.construct;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenBooleanExpression;
import zetta.token.TokenConstruct;
import zetta.token.TokenMethodCall;
import zetta.token.TokenVariable;

/**
 * The while loop Format: while(Condition){ statements... }
 * 
 * @author samueltebbs, 12:45:51 - 26 Jul 2014
 */
public class ConstructWhile extends ConstructIf {

    public ConstructWhile(final String name) {
	super(name);
    }

    @Override
    public boolean canConstruct(final Token[] tokens, final TokenConstruct t) {
	if (Method.checkLength(tokens, true, 1)) {
	    if ((tokens[0] instanceof TokenBooleanExpression)
		    || (tokens[0] instanceof TokenVariable)
		    || (tokens[0] instanceof TokenMethodCall)) return true;
	    else {
		new UnexpectedTokenException(tokens[0],
			Zetta.tokenBooleanExpression, Zetta.tokenVariable,
			Zetta.tokenMethodCall);
	    }

	}
	return false;
    }

    @Override
    public Variable run(final TokenConstruct token) {
	boolean succeeded = false;
	do {
	    succeeded = false;
	    if (token.subTokens[0] instanceof TokenBooleanExpression) {
		if (Boolean.parseBoolean(token.subTokens[0].evaluate()
			.toString())) {
		    succeeded = true;
		}
	    } else if (token.subTokens[0] instanceof TokenVariable) {
		final Variable v = Zetta.getVariableOfType(token.subTokens[0],
			Zetta.dtBoolean);
		if (Boolean.parseBoolean(v.dType.get(v).toString())) {
		    succeeded = true;
		}
	    } else if (token.subTokens[0] instanceof TokenMethodCall) {
		final Object obj = Zetta
			.executeMethod((TokenMethodCall) token.subTokens[0]);
		if (obj != null) {
		    if (Boolean.parseBoolean(obj.toString())) {
			succeeded = true;
		    }
		}
	    }
	    if (succeeded) {
		Zetta.runFromConstruct(token.body, token, true);
	    }
	} while (succeeded);
	return null;
    }

}
