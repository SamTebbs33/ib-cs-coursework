package zetta.lang.construct;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Construct;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.token.TokenConstruct;
import zetta.token.TokenMethodArgument;
import zetta.token.TokenString;

/**
 * 
 * @author samueltebbs, 10:57:24 - 2 Aug 2014
 */
public class ConstructStruct extends Construct {

    public ConstructStruct(final String name) {
	super(name);
    }

    @Override
    public boolean canConstruct(final Token[] tokens, final TokenConstruct t) {
	if (Method.checkLength(tokens, true, 1)) {
	    if (tokens[0] instanceof TokenString) {
		for (final Token t2 : t.body) {
		    if ((t2 instanceof TokenMethodArgument)) {} else {
			new UnexpectedTokenException(t2,
				Zetta.tokenMethodArgument);
		    }
		}
		return true;
	    } else {
		new UnexpectedTokenException(tokens[0], Zetta.tokenString);
	    }
	}
	return false;
    }

}
