package zetta.token;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;

/**
 * Zetta
 * 
 * @author samueltebbs, 12:59:16 - 24 Jul 2014
 */
public class TokenMethodArgument extends Token {

    public DataType dType;

    public TokenMethodArgument(final DataType dType2, final String variable) {
	super(variable);
	dType = dType2;
	Zetta.compVariables.put(variable, dType.name);
    }

    public TokenMethodArgument() {
	super();
    }

    @Override
    public Token getTokenFromString(final String str) {
	if (containsBooleanOperators(str)) return null;
	if (containsMathsOperators(str)) return null;
	final int commaInd = indexOf(str, ',', true);
	final int openInd = indexOfOpeningBracket(str, '(');
	final int closeInd = indexOfClosingBracket(
		commaInd > openInd ? str.substring(0, commaInd) : str, ')');
	if ((closeInd > openInd) && (openInd > 3)) {

	} else {
	    if (openInd == -1) {
		if (closeInd == -1) {
		    final int varInd = indexOf(str, '$', true);
		    if ((varInd > 0) && ((str.length() - 1) > varInd)) {
			final Token t = getTokenType(str.substring(0, varInd));
			if (t instanceof TokenDataType) return new TokenMethodArgument(
				((TokenDataType) t).dType,
				str.substring(varInd + 1));
		    }
		}
	    }
	}

	return null;
    }

}
