package zetta.token;

import zetta.Zetta;
import zetta.exception.InvalidInputException;
import zetta.exception.MathematicalException;
import zetta.exception.UnexpectedArgumentQuantityException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.IterableDataType;

public class TokenExpression extends Token {

    public boolean isVariableExpression = false;

    public TokenExpression(final String str) {
	super(str);
	// TODO Auto-generated constructor stub
    }

    public TokenExpression() {
	// TODO Auto-generated constructor stub
	super();
    }

    public static String string() {
	return "expression";
    }

    @Override
    public Token getTokenFromString(final String str) {
	if (containsMathsOperators(str) && !isMathsOperator(str)) {
	    if (isExpression(str)) return expression(str);
	}
	return null;
    }

    @Override
    public Object evaluate() {
	if ((subTokens.length % 2) == 0) {
	    new UnexpectedArgumentQuantityException(subTokens.length, 3);
	    return null;
	}
	final Token first = subTokens[0];
	if (!((first instanceof TokenNumber)
		|| (first instanceof TokenMethodCall)
		|| (first instanceof TokenString) || (first instanceof TokenVariable))) {
	    new UnexpectedTokenException(first, Zetta.tokenNumber,
		    Zetta.tokenMethodCall, Zetta.tokenString,
		    Zetta.tokenVariable);
	    return null;
	}
	double result = 0;
	final Token[] tokens = new Token[subTokens.length + 1];
	tokens[0] = new TokenOperator("+");
	for (int c = 1; c < tokens.length; c++) {
	    tokens[c] = subTokens[c - 1];
	}
	subTokens = tokens;
	for (int c = 0; c < subTokens.length; c += 2) {
	    double value = 0;
	    final Token t1 = subTokens[c], t2 = subTokens[c + 1];
	    if (!(t1 instanceof TokenOperator)) {
		new UnexpectedTokenException(t1, Zetta.tokenOperator);
	    }
	    if (t2 instanceof TokenNumber) {
		value = Double.parseDouble(t2.value);
	    } else if (t2 instanceof TokenString) {
		if (Token.isNumber(t2.value)) {
		    value = Double.parseDouble(t2.value);
		}
	    } else if (t2 instanceof TokenVariable) {
		final Variable v = Zetta.getVariableOfType(t2);
		if (v.dType instanceof IterableDataType) {
		    int total = 0;
		    for (final int k = 0; k < ((IterableDataType) v.dType)
			    .iteratorLength(v); c++) {
			if (Token.isNumber(((IterableDataType) v.dType)
				.iteratorValue(v, k).toString())) {
			    total += Double
				    .parseDouble(((IterableDataType) v.dType)
					    .iteratorValue(v, k).toString());
			} else {
			    new InvalidInputException(Zetta.dtString,
				    Zetta.dtNumber);
			    System.exit(0);
			}
		    }
		    value = total;
		} else {
		    if (Token.isNumber(v.dType.string(v))) {
			value += Double.parseDouble(v.dType.string(v));
		    } else {
			new InvalidInputException(Zetta.dtString,
				Zetta.dtNumber);
		    }
		}
	    } else if (t2 instanceof TokenMethodCall) {
		final String str = ((TokenMethodCall) t2).method.execute(
			t2.subTokens, ((TokenMethodCall) t2).object).toString();
		if (Token.isNumber(str)) {
		    value = Double.parseDouble(str);
		} else {
		    new InvalidInputException(Zetta.dtString, Zetta.dtNumber);
		    System.exit(0);
		}
	    }
	    switch (t1.value.toString()) {
		case "+":
		    result += value;
		    break;
		case "-":
		    result -= value;
		    break;
		case "/":
		    if (value == 0) {
			new MathematicalException("Dividing by zero");
		    }
		    result /= value;
		    break;
		case "*":
		    result *= value;
		    break;
		case "%":
		    result %= value;
	    }
	}
	return result;
    }

}
