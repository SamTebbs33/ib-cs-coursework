package zetta.token;

import java.util.Arrays;

import zetta.Zetta;
import zetta.exception.InvalidSyntaxException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * Zetta
 * 
 * @author samueltebbs, 07:40:13
 */
public class TokenSequence extends Token {

    public TokenSequence(final String str) {
	super(str);
    }

    @Override
    public Object evaluate() {
	String str = "[";
	for (int c = 0; c < subTokens.length; c++) {
	    if (subTokens[c] instanceof TokenLiteral) {
		str += subTokens[c].value;
	    } else if (subTokens[c] instanceof TokenVariable) {
		final Variable v2 = Zetta.getVariableOfType(subTokens[c]);
		str += v2.dType.string(v2);
	    } else if (subTokens[c] instanceof TokenMethodCall) {
		str += Zetta.executeMethod((TokenMethodCall) subTokens[c]);
	    } else if (subTokens[c] instanceof TokenSequence) {
		str += subTokens[c].evaluate();
	    }
	    if ((c + 1) < subTokens.length) {
		str += ",";
	    }
	}
	str += "]";
	return str;
    }

    public TokenSequence() {
	super();
    }

    @Override
    public Token getTokenFromString(final String str) {

	if (str.startsWith("[") && str.endsWith("]")) {
	    final String[] sequence = split(str.substring(1, str.length() - 1));
	    final Token t2 = new TokenSequence();
	    t2.subTokens = new Token[sequence.length];
	    int i = 0;
	    for (int c = 0; c < sequence.length; c++) {
		final int colonPos = indexOf(sequence[c], ':', true);
		if (colonPos > 0) {
		    if (sequence[c].length() >= 3) {
			if (isNumber(sequence[c].substring(0, colonPos))
				&& isNumber(sequence[c].substring(colonPos + 1,
					sequence[c].length()))) {
			    final int intA = (int) Double
				    .parseDouble(sequence[c].substring(0,
					    colonPos)), intB = (int) Double
				    .parseDouble(sequence[c].substring(
					    colonPos + 1, sequence[c].length()));
			    if (intA < intB) {
				final int subSequenceLength = intB - intA;
				t2.subTokens = Arrays
					.copyOf(t2.subTokens,
						t2.subTokens.length
							+ subSequenceLength);
				for (int k = 0; k <= subSequenceLength; k++) {
				    t2.subTokens[i + k] = new TokenNumber(
					    String.valueOf(intA + k));
				}
				i += subSequenceLength;
			    } else {
				final int subSequenceLength = intA - intB;
				t2.subTokens = Arrays
					.copyOf(t2.subTokens,
						t2.subTokens.length
							+ subSequenceLength);
				for (int k = 0; k <= subSequenceLength; k++) {
				    t2.subTokens[i + k] = new TokenNumber(
					    String.valueOf(intA - k));
				}
				i += subSequenceLength;
			    }
			} else {
			    if (sequence[c].length() == 3) {
				final char charA = sequence[c].charAt(0), charB = sequence[c]
					.charAt(2);
				if (charA < charB) {
				    final int subSequenceLength = charB - charA;
				    t2.subTokens = Arrays.copyOf(t2.subTokens,
					    t2.subTokens.length
						    + subSequenceLength);
				    for (int k = 0; k <= subSequenceLength; k++) {
					t2.subTokens[i + k] = new TokenString(
						"d"
							+ String.valueOf((char) (charA + k))
							+ "d");
				    }
				    i += subSequenceLength;
				} else {
				    final int subSequenceLength = charA - charB;
				    t2.subTokens = Arrays.copyOf(t2.subTokens,
					    t2.subTokens.length
						    + subSequenceLength);
				    for (int k = 0; k <= subSequenceLength; k++) {
					t2.subTokens[i + k] = new TokenString(
						"d"
							+ String.valueOf((char) (charA - k))
							+ "d");
				    }
				    i += subSequenceLength;
				}
			    } else {
				new InvalidSyntaxException(sequence[c]);
			    }
			}
		    } else {
			new InvalidSyntaxException(sequence[c]);
		    }
		} else {
		    final Token t = getTokenType(sequence[c]);
		    if ((t instanceof TokenLiteral)
			    || (t instanceof TokenMethodCall)
			    || (t instanceof TokenVariable)
			    || (t instanceof TokenSequence)) {
			t2.subTokens[i] = t;
		    } else {
			new UnexpectedTokenException(t);
		    }
		}
		i++;
	    }
	    t2.value = str;
	    return t2;
	}
	return null;
    }

}
