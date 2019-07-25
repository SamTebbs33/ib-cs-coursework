package zetta.token;

import java.util.Set;

import zetta.Zetta;
import zetta.exception.InvalidDataTypeException;
import zetta.lang.DataType;
import zetta.lang.Token;

public class TokenVariable extends TokenPlaceholder {

    public Token subScript = null;
    public DataType dType;

    public TokenVariable(final String str, final Token sub) {
	super(str);
	subScript = sub;
	final Set<String> keys = Zetta.compVariables.keySet();
	for (final String key : keys) {
	    if (key.equals(value)) {
		for (final DataType d : Zetta.dataTypes) {
		    if (d.name.equals(Zetta.compVariables.get(key))) {
			dType = d;
			return;
		    }
		}
		new InvalidDataTypeException(Zetta.compVariables.get(key));
		return;
	    }
	}
    }

    public TokenVariable() {
	// TODO Auto-generated constructor stub
	super();
    }

    public static String string() {
	return "variable";
    }

    @Override
    public Token getTokenFromString(final String str) {
	if (str.length() > 1) {
	    if (containsNoOperators(str)) {
		if (indexOf(str, '$', true) == 0) {
		    if (indexOf(str, '.', true) == -1) {
			final int indexOpen = indexOfOpeningBracket(str, '[');
			final int indexClose = indexOfClosingBracket(str, ']');
			if (indexOpen > 1) {
			    if ((indexClose == (str.length() - 1))
				    && (indexClose > (indexOpen + 1))) {
				final Token t = Token.getTokenType(str
					.substring(indexOpen + 1, indexClose));
				if ((t instanceof TokenPlaceholder)
					|| (t instanceof TokenLiteral)) {
				    final Token t2 = new TokenVariable(
					    str.substring(1, indexOpen), t);
				    ((TokenVariable) t2).subScript = t;
				    return t2;
				}
			    }
			    return null;
			}
			return new TokenVariable(str.substring(1), null);
		    }
		}
	    }
	}
	return null;
    }

}
