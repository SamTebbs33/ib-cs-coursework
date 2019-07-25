package zetta.token;

import zetta.lang.Token;

/**
 * 
 */
public class TokenBooleanOperator extends Token {

    public TokenBooleanOperator(final String str) {
	super(str);
	// TODO Auto-generated constructor stub
    }

    public TokenBooleanOperator() {
	// TODO Auto-generated constructor stub
	super();
    }

    @Override
    public Token getTokenFromString(final String str) {
	if (isBooleanOperator(str)) return new TokenBooleanOperator(str);
	return null;
    }

}
