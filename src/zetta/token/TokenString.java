package zetta.token;

import zetta.lang.Token;

/**
 * @(#)TokenString.java
 * 
 * 
 * @author
 * @version 1.00 2014/6/27
 */

public class TokenString extends TokenLiteral {
    public static String name = "string";
    public String raw = "";

    public TokenString(final String str) {
	super(str.substring(1, str.length() - 1));
	raw = str;
    }

    public TokenString() {
	// TODO Auto-generated constructor stub
	super();
    }

    @Override
    public Object evaluate() {
	return raw;
    }

    public static String string() {
	return "string";
    }

    @Override
    public Token getTokenFromString(final String str) {
	return (str.startsWith("\"") && str.endsWith("\"") ? new TokenString(
		str) : null);
    }

}