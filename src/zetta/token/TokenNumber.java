package zetta.token;

import zetta.lang.Token;

/**
 * @(#)TokenInteger.java
 * 
 * 
 * @author
 * @version 1.00 2014/6/27
 */

public class TokenNumber extends TokenLiteral {

    public TokenNumber(final String d) {
	super(d);
    }

    public TokenNumber() {
	// TODO Auto-generated constructor stub
	super();
    }

    public static String string() {
	return "integer";
    }

    @Override
    public Token getTokenFromString(final String str) {
	if (isNumber(str) && !isInteger(str)) return new TokenNumber(str);
	return null;
    }

}