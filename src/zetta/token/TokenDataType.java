package zetta.token;

import zetta.Zetta;
import zetta.exception.InvalidDataTypeException;
import zetta.lang.DataType;
import zetta.lang.Token;

public class TokenDataType extends Token {

    public DataType dType;

    public TokenDataType(final String value) {
	super(value);
	this.value = value;
	for (final DataType d : Zetta.dataTypes) {
	    if (d.toString().equals(value)) {
		dType = d;
		return;
	    }
	}
	new InvalidDataTypeException(value);
    }

    public TokenDataType() {
	super();
    }

    public static String string() {
	return "data type";
    }

    @Override
    public Token getTokenFromString(final String str) {
	if ((str.charAt(0) <= 'Z') && (str.charAt(0) >= 'A')
		&& !str.contains(".")) {
	    final int commaInd = indexOf(str, ',', true);
	    final int openInd = indexOfOpeningBracket(str, '(');
	    final int closeInd = indexOfClosingBracket(
		    commaInd > openInd ? str.substring(0, commaInd) : str, ')');

	    if ((openInd == -1)) {
		if (closeInd == -1) {
		    final int varInd = indexOf(str, '$', true);
		    if (varInd == -1) {
			if (str.length() > 1) return new TokenDataType(str);
		    }
		}
	    }
	}

	return null;
    }

}
