package zetta.token;

import zetta.lang.Token;

/**
 * 
 * @author samueltebbs, 21:49:51 - 17 Sep 2014
 */
public class TokenInteger extends TokenLiteral {

    public int intVal;

    public TokenInteger(final String str) {
	super(str);
	intVal = Integer.parseInt(value);
    }

    public TokenInteger() {}

    @Override
    public Token getTokenFromString(final String str) {
	return (isInteger(str) ? new TokenInteger(str) : null);
    }

}
