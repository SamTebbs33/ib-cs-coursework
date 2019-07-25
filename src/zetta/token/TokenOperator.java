package zetta.token;

import zetta.lang.Token;

public class TokenOperator extends Token {

    public TokenOperator(final String str) {
	super(str);
	// TODO Auto-generated constructor stub
    }

    public TokenOperator() {
	// TODO Auto-generated constructor stub
	super();
    }

    @Override
    public Token getTokenFromString(final String str) {
	if (isMathsOperator(str)) return new TokenOperator(str);
	return null;
    }

}
