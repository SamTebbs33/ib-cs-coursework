package zetta.token;

import java.util.Arrays;

import zetta.Zetta;
import zetta.exception.InvalidDataTypeException;
import zetta.exception.ReservedVarNameException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Token;

public class TokenVariableDeclaration extends Token {

    public TokenVariableDeclaration[] subDeclarations = new TokenVariableDeclaration[0];

    public TokenVariableDeclaration(final String[] strings) {
	super("");
	final String[] args = Token.split(strings[2]);
	subTokens = new Token[2 + args.length];
	subTokens[0] = Token.getTokenType(strings[0]);
	subTokens[1] = Token.getTokenType(strings[1]);
	if (subTokens[1].value.equals(Zetta.RESERVED_VAR_NAME)) {
	    new ReservedVarNameException(subTokens[1].value);
	}
	for (int c = 0; c < args.length; c++) {
	    final Token t = Token.getTokenType(args[c]);
	    if ((t instanceof TokenVariable) || (t instanceof TokenMethodCall)
		    || (t instanceof TokenExpression)
		    || (t instanceof TokenNumber) || (t instanceof TokenString)
		    || (t instanceof TokenInteger)
		    || (t instanceof TokenBooleanExpression)
		    || (t instanceof TokenSequence)
		    || (t instanceof TokenDataType)) {
		subTokens[c + 2] = t;
	    } else {
		new UnexpectedTokenException(t, Zetta.tokenVariable,
			Zetta.tokenMethodCall, Zetta.tokenExpression,
			Zetta.tokenNumber, Zetta.tokenInteger,
			Zetta.tokenString, Zetta.tokenBooleanExpression,
			Zetta.tokenSequence);
		return;
	    }
	}
	for (int c = 0; c < Zetta.dataTypes.size(); c++) {
	    if (subTokens[0] != null) {
		if (subTokens[0].value
			.equals(Zetta.dataTypes.get(c).toString())) {
		    if (Zetta.dataTypes.get(c).canConstruct(
			    Arrays.copyOfRange(subTokens, 2, subTokens.length),
			    "")) {
			Zetta.compVariables.put(strings[1].substring(1),
				strings[0]);
			return;
		    }
		}
	    }
	}
	new InvalidDataTypeException(subTokens[0].value);
    }

    public TokenVariableDeclaration() {
	// TODO Auto-generated constructor stub
	super();
    }

    public static String string() {
	return "variable declaration";
    }

    @Override
    public Token getTokenFromString(final String str) {
	if (containsNoOperators(str)) {

	    if ((str.charAt(0) <= 'Z') && (str.charAt(0) >= 'A')) { // Makes
								    // sure
								    // there
								    // is
								    // a
								    // spot
								    // in
		// between the $ and .
		final int commaInd = indexOf(str, ',', true);
		final int openInd = indexOfOpeningBracket(str, '(');
		final int closeInd = indexOfClosingBracket(
			commaInd > openInd ? str.substring(0, commaInd) : str,
			')');
		if ((closeInd > openInd) && (openInd > 3)) {
		    final int fullStopInd = indexOf(str, '.', true);
		    if (fullStopInd < 1) {
			final int varInd = indexOf(str, '$', true);
			if (varInd > 0) {
			    if ((openInd > (varInd + 1))
				    && (closeInd > openInd)) {
				int last = closeInd;
				if (commaInd > closeInd) {
				    last = commaInd;
				}
				final TokenVariableDeclaration t = new TokenVariableDeclaration(
					new String[] {
						str.substring(0, varInd),
						str.substring(varInd, openInd),
						str.substring(openInd + 1, last) });
				if (commaInd > closeInd) {
				    final String[] declarations = split(str
					    .substring(commaInd + 1));
				    t.subDeclarations = new TokenVariableDeclaration[declarations.length];
				    for (int c = 0; c < declarations.length; c++) {
					final Token t2 = getTokenType(str
						.substring(0, varInd)
						+ declarations[c]);
					if (t2 instanceof TokenVariableDeclaration) {
					    t.subDeclarations[c] = (TokenVariableDeclaration) t2;
					} else {
					    new UnexpectedTokenException(t2,
						    Zetta.tokenVariableDec);
					}
				    }
				}
				return t;
			    }
			}

		    }
		}
	    }
	}
	return null;
    }

    @Override
    public boolean runToken() {
	Zetta.doVariableDeclaration(this);
	for (int c = 0; c < (this).subDeclarations.length; c++) {
	    Zetta.doVariableDeclaration(subDeclarations[c]);
	}
	return true;
    }

}
