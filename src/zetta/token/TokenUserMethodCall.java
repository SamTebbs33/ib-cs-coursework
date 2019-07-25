package zetta.token;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Token;

/**
 * Zetta
 * 
 * @author samueltebbs, 14:18:27 - 24 Jul 2014
 */
public class TokenUserMethodCall extends TokenMethodCall {

    public TokenUserMethodCall(final String substring, final String[] args) {
	super(substring.substring(1));
	System.out.println("user method call " + substring);
	subTokens = new Token[args.length];
	for (int c = 0; c < args.length; c++) {
	    final Token t = Token.getTokenType(args[c]);
	    if ((t instanceof TokenVariable) || (t instanceof TokenMethodCall)
		    || (t instanceof TokenExpression)
		    || (t instanceof TokenNumber) || (t instanceof TokenString)
		    || (t instanceof TokenBooleanExpression)
		    || (t instanceof TokenSequence)) {
		subTokens[c] = t;
	    } else {
		new UnexpectedTokenException(t, Zetta.tokenVariable,
			Zetta.tokenMethodCall, Zetta.tokenExpression,
			Zetta.tokenNumber, Zetta.tokenString,
			Zetta.tokenBooleanExpression, Zetta.tokenSequence);
		return;
	    }
	}
    }

    public TokenUserMethodCall() {
	super();
    }

    @Override
    public Token getTokenFromString(final String str) {

	if (str.startsWith("_") && containsNoOperators(str)) {
	    final int openInd = indexOfOpeningBracket(str, '(');
	    final int closeInd = indexOfClosingBracket(str, ')');
	    if ((closeInd > openInd) && (openInd > 1)) {
		final int fullStopInd = indexOf(str, '.', true);
		if (fullStopInd < 0) return new TokenUserMethodCall(
			str.substring(0, openInd), split(str.substring(
				openInd + 1, closeInd)));
	    }
	}
	return null;
    }

}
