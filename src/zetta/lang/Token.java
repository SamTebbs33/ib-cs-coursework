package zetta.lang;

import java.util.LinkedList;

import zetta.Zetta;
import zetta.exception.InvalidSyntaxException;
import zetta.exception.UnexpectedDataTypeException;
import zetta.exception.UnexpectedTokenException;
import zetta.exception.VoidReturnTypeException;
import zetta.token.TokenBooleanExpression;
import zetta.token.TokenBooleanOperator;
import zetta.token.TokenExpression;
import zetta.token.TokenInteger;
import zetta.token.TokenLiteral;
import zetta.token.TokenMethodArgument;
import zetta.token.TokenMethodCall;
import zetta.token.TokenNumber;
import zetta.token.TokenOperator;
import zetta.token.TokenSequence;
import zetta.token.TokenString;
import zetta.token.TokenVariable;

/**
 * Represents a sequence of characters that mean something to the interpreter,
 * e.g. a number, method call or variable declaration
 * 
 * @author samueltebbs
 * 
 */
public class Token {

    public String value;

    /**
     * The tokens that are included in this token
     */
    public Token[] subTokens;
    private static final char[] MATHS_OPERATORS = { '*', '%', '-', '+', '/' };
    private static final char[] BOOLEAN_OPERATORS = { '!', '^', '&', '=', '|',
	    '<', '>' };
    /**
     * The char used to represent an invalid integer. Currently unused as
     * exceptions are thrown instead.
     */
    public static final char INVALID_INT = '#';

    public Token(final String str) {
	value = str;
    }

    public Token() {
	Zetta.tokens.add(this);
	Zetta.currentLib.tokens.add(this);
    }

    public Object evaluate() {
	return value;
    }

    protected static boolean isExpression(final String str) {
	final String[] result = splitOperators(str);
	for (final String element : result) {
	    final Token t = getTokenType(element);
	    if ((t instanceof TokenNumber) || (t instanceof TokenMethodCall)
		    || (t instanceof TokenString)
		    || (t instanceof TokenVariable)
		    || (t instanceof TokenOperator)) {} else return false;
	}
	return true;
    }

    protected static Token expression(final String str) {
	final String[] result = splitOperators(str);
	final TokenExpression te = new TokenExpression();
	te.subTokens = new Token[result.length];
	for (int c = 0; c < result.length; c++) {
	    final Token t = getTokenType(result[c]);
	    te.subTokens[c] = t;
	}
	return te;
    }

    /**
     * Finds the index of the unescaped opening bracket outside of speech marks
     */
    public static int indexOfOpeningBracket(final String string,
	    final char bracketType) {
	int quotes = 0;
	final int result = -1;
	for (int c = 0; c < string.length(); c++) {
	    final char chr = string.charAt(c);
	    if (chr == 92) {
		c++;
	    } else if (chr == '\"') {
		quotes++;
	    } else if ((chr == bracketType) && ((quotes % 2) == 0)) return c;

	}
	return result;
    }

    /**
     * Finds the index of the unescaped closing bracket
     */
    public static int indexOfClosingBracket(final String string,
	    final char bracketType) {
	for (int c = string.length() - 1; c >= 0; c--) {
	    if (string.charAt(c) == 92) {
		c++;
	    } else if (string.charAt(c) == bracketType) return c;
	}
	return -1;
    }

    /**
     * Finds the index of the unescaped char outside of speech marks and
     * brackets
     */
    public static int indexOf(final String string, final char ch,
	    final boolean first) {
	int brackets = 0, quotes = 0, result = -1, sbrackets = 0;
	for (int c = 0; c < string.length(); c++) {
	    final char chr = string.charAt(c);
	    if (chr == '\"') {
		quotes++;
	    } else if (chr == 92) {
		c++;
	    } else if ((chr == '(') && (chr != ch)) {
		brackets++;
	    } else if ((chr == ')') && (chr != ch)) {
		brackets--;
	    } else if ((chr == '[') && (chr != ch)) {
		sbrackets++;
	    } else if ((chr == ']') && (chr != ch)) {
		sbrackets--;
	    } else if (chr == ch) {
		if ((brackets == 0) && ((quotes % 2) == 0) && (sbrackets == 0)) {
		    if (first) return c;
		    result = c;

		}
	    }
	}
	return result;
    }

    /**
     * Splits the String by unescaped commas outside of speech marks and
     * brackets
     */
    public static String[] split(final String string) {
	final LinkedList<String> strings = new LinkedList<String>();
	String str = "";
	int brackets = 0, quotes = 0, sbrackets = 0;
	for (int c = 0; c < string.length(); c++) {
	    final char ch = string.charAt(c);
	    switch (ch) {
		case 92:
		    c++;
		    break;
		case '\"':
		    quotes++;
		    str += "\"";
		    break;
		case '(':
		    brackets++;
		    if (brackets > 0) {
			str += "(";
		    }
		    break;
		case ')':
		    brackets--;
		    if (brackets >= 0) {
			str += ")";
		    }
		    break;
		case '[':
		    sbrackets++;
		    if (sbrackets > 0) {
			str += "[";
		    }
		    break;
		case ']':
		    sbrackets--;
		    if (sbrackets >= 0) {
			str += "]";
		    }
		    break;
		case ',':
		    if ((brackets == 0) && ((quotes % 2) == 0)
			    && (sbrackets == 0)) {
			if (!str.equals("")) {
			    strings.add(str);
			}
			str = "";
		    } else {
			str += ",";
		    }
		    break;
		default:
		    str += String.valueOf(ch);
		    break;
	    }
	}
	if (!str.equals("")) {
	    strings.add(str);
	}
	final String[] array = new String[strings.size()];
	for (int c = 0; c < strings.size(); c++) {
	    array[c] = strings.get(c);
	}
	return array;
    }

    /**
     * Splits the String by unescaped commas outside of speech marks and
     * brackets
     */
    public static String[] splitByDot(final String string) {
	final LinkedList<String> strings = new LinkedList<String>();
	String str = "";
	int brackets = 0, quotes = 0, sbrackets = 0;
	for (int c = 0; c < string.length(); c++) {
	    final char ch = string.charAt(c);
	    switch (ch) {
		case 92:
		    c++;
		    break;
		case '\"':
		    quotes++;
		    str += "\"";
		    break;
		case '(':
		    brackets++;
		    if (brackets > 0) {
			str += "(";
		    }
		    break;
		case ')':
		    brackets--;
		    if (brackets >= 0) {
			str += ")";
		    }
		    break;
		case '[':
		    sbrackets++;
		    if (sbrackets > 0) {
			str += "[";
		    }
		    break;
		case ']':
		    sbrackets--;
		    if (sbrackets >= 0) {
			str += "]";
		    }
		    break;
		case '.':
		    if ((brackets == 0) && ((quotes % 2) == 0)
			    && (sbrackets == 0)) {
			if (!str.equals("")) {
			    strings.add(str);
			}
			str = "";
		    } else {
			str += ".";
		    }
		    break;
		default:
		    str += String.valueOf(ch);
		    break;
	    }
	}
	if (!str.equals("")) {
	    strings.add(str);
	}
	final String[] array = new String[strings.size()];
	for (int c = 0; c < strings.size(); c++) {
	    array[c] = strings.get(c);
	}
	return array;
    }

    /**
     * Splits the String by unescaped boolean operators outside of speech marks
     * and brackets
     */
    protected static String[] splitBOperators(final String string) {
	final LinkedList<String> strings = new LinkedList<String>();
	String str = "";
	int brackets = 0, quotes = 0, sbrackets = 0;
	for (int c = 0; c < string.length(); c++) {
	    final char ch = string.charAt(c);
	    switch (ch) {
		case 92:
		    c++;
		    break;
		case '\"':
		    quotes++;
		    str += "\"";
		    break;
		case '(':
		    brackets++;
		    if (brackets > 0) {
			str += "(";
		    }
		    break;
		case ')':
		    brackets--;
		    if (brackets >= 0) {
			str += ")";
		    }
		    break;
		case '[':
		    sbrackets++;
		    if (sbrackets > 0) {
			str += "[";
		    }
		    break;
		case ']':
		    sbrackets--;
		    if (sbrackets >= 0) {
			str += "]";
		    }
		    break;
		case ',':
		    if ((brackets == 0) && ((quotes % 2) == 0)
			    && (sbrackets == 0)) {
			if (!str.equals("")) {
			    strings.add(str);
			}
			str = "";
		    } else {
			str += ",";
		    }
		    break;
		case '<':
		case '>':
		case '=':
		case '!':
		case '&':
		case '|':
		    if ((brackets == 0) && ((quotes % 2) == 0)
			    && (sbrackets == 0)) {
			if (!str.equals("")) {
			    strings.add(str);
			}
			strings.add(String.valueOf(ch));
			str = "";
		    } else {
			str += String.valueOf(ch);
		    }
		    break;
		default:
		    str += String.valueOf(ch);
		    break;
	    }
	}
	if (!str.equals("")) {
	    strings.add(str);
	}
	final String[] array = new String[strings.size()];
	for (int c = 0; c < strings.size(); c++) {
	    array[c] = strings.get(c);
	}
	return array;
    }

    /**
     * Splits the String by unescaped maths operators outside of speech marks
     * and brackets
     */
    protected static String[] splitOperators(final String string) {
	final LinkedList<String> strings = new LinkedList<String>();
	String str = "";
	int brackets = 0, quotes = 0;
	for (int c = 0; c < string.length(); c++) {
	    final char ch = string.charAt(c);
	    switch (ch) {
		case 92:
		    c++;
		    break;
		case '\"':
		    quotes++;
		    str += "\"";
		    break;
		case '(':
		    brackets++;
		    if (brackets > 0) {
			str += "(";
		    }
		    break;
		case ')':
		    brackets--;
		    if (brackets >= 0) {
			str += ")";
		    }
		    break;
		case '+':
		case '-':
		case '*':
		case '/':
		case '%':
		    if ((brackets == 0) && ((quotes % 2) == 0)) {
			strings.add(str);
			strings.add(String.valueOf(ch));
			str = "";
		    } else {
			str += String.valueOf(ch);
		    }
		    break;
		default:
		    str += String.valueOf(ch);
		    break;
	    }
	}
	strings.add(str);
	final String[] array = new String[strings.size()];
	for (int c = 0; c < strings.size(); c++) {
	    array[c] = strings.get(c);
	}
	return array;
    }

    /**
     * Returns true if the String can be parsed as a positive or negative double
     */
    public static boolean isNumber(final String str) {
	if (str != null) {
	    if (!str.equals("-")) {
		if (str.equals("")) return false;
		if (str.contains(".")) {
		    if (str.indexOf('.') == str.lastIndexOf('.')) {
			if (isNumber(str.substring(0, str.indexOf('.')))) {
			    if (isNumber(str.substring(str.indexOf('.') + 1))) return true;
			}
		    }
		    return false;
		}
		for (int c = 0; c < str.length(); c++) {
		    final char ch = str.charAt(c);
		    if (((ch <= '9') && (ch >= '0'))
			    || ((c == 0) && (ch == '-'))) {
			continue;
		    } else return false;
		}
		return true;
	    }
	}
	return false;
    }

    /**
     * Returns true if the String can be parsed as a positive or negative
     * integer
     */
    public static boolean isInteger(final String str) {
	if (str != null) {
	    if (!str.equals("-")) {
		if (str.equals("")) return false;
		for (int c = 0; c < str.length(); c++) {
		    final char ch = str.charAt(c);
		    if (((ch <= '9') && (ch >= '0'))
			    || ((c == 0) && (ch == '-'))) {
			continue;
		    } else return false;
		}
		return true;
	    }
	}
	return false;
    }

    /**
     * Gets a boolean expression from the String
     */
    protected static Token booleanExpression(final String str) {
	final String[] args = splitBOperators(str);
	final Token result = new TokenBooleanExpression(str);
	result.subTokens = new Token[args.length];
	for (int c = 0; c < args.length; c++) {
	    final Token t = getTokenType(args[c]);
	    if ((c % 2) == 0) {
		if ((t instanceof TokenLiteral) || (t instanceof TokenSequence)
			|| (t instanceof TokenMethodCall)
			|| (t instanceof TokenVariable)
			|| (t instanceof TokenExpression)
			|| (t instanceof TokenBooleanExpression)
			|| (t instanceof TokenMethodArgument)) {

		} else {
		    new UnexpectedTokenException(t, Zetta.tokenNumber,
			    Zetta.tokenMethodCall, Zetta.tokenString,
			    Zetta.tokenSequence, Zetta.tokenMethodArgument,
			    Zetta.tokenVariable, Zetta.tokenBooleanExpression);
		    return null;
		}
	    } else {
		if (t instanceof TokenBooleanOperator) {

		} else {
		    new UnexpectedTokenException(t, Zetta.tokenBooleanOperator);
		}
	    }
	    result.subTokens[c] = t;
	}
	return result;
    }

    /**
     * Gets a double from the token
     */

    public static double getDouble(final Token t) {
	if (t instanceof TokenNumber) return Double.parseDouble(t.value);
	else if (t instanceof TokenMethodCall) {
	    final Variable v = Zetta.executeMethod((TokenMethodCall) t);
	    if (v.dType == Zetta.dtNumber) return Double.parseDouble(v
		    .getIndex(0).toString());
	    else if (isNumber(v.dType.string(v))) return Double.parseDouble(v
		    .getIndex(0).toString());
	    else {
		new UnexpectedDataTypeException(
			Zetta.getMethodReturnType((TokenMethodCall) t),
			Zetta.dtNumber);
	    }
	} else if (t instanceof TokenVariable) {
	    final Variable var = Zetta.getVariableOfType(t, Zetta.dtNumber);
	    return Double.parseDouble(var.dType.string(var));
	} else if (t instanceof TokenExpression) return (double) t.evaluate();
	return -1;
    }

    /**
     * Gets a double from the token
     */
    public static int getInteger(final Token t) {
	if (t instanceof TokenInteger) return ((TokenInteger) t).intVal;
	else if (t instanceof TokenMethodCall) {
	    final Variable v = Zetta.executeMethod((TokenMethodCall) t);
	    if (v.dType == Zetta.dtInteger) return (int) v.getIndex(0);
	    else {
		new UnexpectedDataTypeException(
			Zetta.getMethodReturnType((TokenMethodCall) t),
			Zetta.dtInteger);
	    }
	} else if (t instanceof TokenVariable) {
	    final Variable var = Zetta.getVariableOfType(t, Zetta.dtInteger);
	    return (int) var.getIndex(0);
	} else if (t instanceof TokenExpression) return (int) t.evaluate();
	return -1;
    }

    /**
     * Gets a String from the token
     */
    public static String getString(final Token t) {
	if (t instanceof TokenNumber) return t.value;
	else if (t instanceof TokenMethodCall) {
	    final DataType d = Zetta.getMethodReturnType((TokenMethodCall) t);
	    if (d != Zetta.dtNull) {
		final Variable v = Zetta.executeMethod((TokenMethodCall) t);
		return v.dType.string(v);
	    } else {
		new VoidReturnTypeException(t);
	    }
	} else if (t instanceof TokenVariable) {
	    final Variable var = Zetta.getVariable((TokenVariable) t);
	    return var.dType.string(var);
	} else if (t instanceof TokenString) return t.value;
	return "";
    }

    /**
     * Gets a boolean from the token
     */
    public static boolean getBool(final Token t) {
	if (t instanceof TokenVariable) {
	    final Variable v = Zetta.getVariableOfType(t, Zetta.dtBoolean);
	    return (boolean) v.getIndex(0);
	} else if (t instanceof TokenBooleanExpression) {
	    final String str = t.evaluate().toString();
	    return Boolean.parseBoolean(str);
	} else if (t instanceof TokenMethodCall) {
	    if (Zetta.getMethodReturnType((TokenMethodCall) t) == Zetta.dtBoolean) return (boolean) Zetta
		    .executeMethod((TokenMethodCall) t).getIndex(0);
	}
	return false;
    }

    /**
     * Returns the token type whose grammar matches the String
     */
    public static Token getTokenType(final String str) {
	if (str != null) {
	    if (str.length() > 0) {
		Token token;
		// Loop through all token types and check for matched syntax
		for (final Token t : Zetta.tokens) {
		    if ((token = t.getTokenFromString(str)) != null) return token;
		}
	    }
	}
	new InvalidSyntaxException(str);
	return null;
    }

    public Token getTokenFromString(final String str) {
	return null;
    }

    /**
     * Returns true if the String is a maths operator
     */
    public static boolean isMathsOperator(final String str) {
	if (str.length() == 1) {
	    final char ch2 = str.charAt(0);
	    for (int c = 0; c < MATHS_OPERATORS.length; c++) {
		final char ch = MATHS_OPERATORS[c];
		if (ch == 92) {
		    c++;
		} else if (ch == ch2) return true;
	    }
	}
	return false;
    }

    /**
     * Returns true if the String is a boolean operator
     */
    public static boolean isBooleanOperator(final String str) {
	if (str.length() == 1) {
	    final char ch2 = str.charAt(0);
	    for (int c = 0; c < str.length(); c++) {
		final char ch = str.charAt(c);
		if (ch == 92) {
		    c++;
		    continue;
		}
		if (ch == ch2) return true;
	    }
	}
	return false;
    }

    /**
     * Returns true if the String contains no unescaped operators outside speech
     * marks and brackets
     */
    public static boolean containsNoOperators(final String str) {
	for (int c = 0; c < MATHS_OPERATORS.length; c++) {
	    final char ch = MATHS_OPERATORS[c];
	    if (ch == 92) {
		c++;
		continue;
	    }
	    if (indexOf(str, ch, true) != -1) return false;
	}
	for (int c = 0; c < BOOLEAN_OPERATORS.length; c++) {
	    final char ch = BOOLEAN_OPERATORS[c];
	    if (ch == 92) {
		c++;
		continue;
	    }
	    if (indexOf(str, ch, true) != -1) return false;
	}
	return true;
    }

    /**
     * Returns true if the String contains unescaped maths operators outside
     * speech marks and brackets
     */
    public static boolean containsMathsOperators(final String str) {
	for (int c = 0; c < MATHS_OPERATORS.length; c++) {
	    final char ch = MATHS_OPERATORS[c];
	    if (ch == 92) {
		c++;
		continue;
	    }
	    if (indexOf(str, ch, true) != -1) return true;
	}
	return false;
    }

    /**
     * Returns true if the String contains unescaped boolean operators outside
     * speech marks and brackets
     */
    public static boolean containsBooleanOperators(final String str) {
	for (int c = 0; c < BOOLEAN_OPERATORS.length; c++) {
	    final char ch = BOOLEAN_OPERATORS[c];
	    if (ch == 92) {
		c++;
		continue;
	    }
	    if (indexOf(str, ch, true) != -1) return true;
	}
	return false;
    }

    @Override
    public String toString() {
	final String str = this.getClass().getName();
	if (str.contains(".")) return str.substring(str.lastIndexOf('.') + 1);
	return str + "(" + value + ")";
    }

    public static boolean tokenCanBeNumber(final Token t) {
	if (t instanceof TokenExpression) return true;
	else if (t instanceof TokenMethodCall) return true;
	else if ((t instanceof TokenNumber) || (t instanceof TokenInteger)) return true;
	else if (t instanceof TokenVariable) return true;
	return false;
    }

    public boolean runToken() {
	return false;
    }

    public static Variable getList(final Token t) {
	if (t instanceof TokenVariable) return Zetta.getVariableOfType(t,
		Zetta.dtArray);
	else if (t instanceof TokenMethodCall) {
	    if (Zetta.getMethodReturnType((TokenMethodCall) t) == Zetta.dtArray) return Zetta
		    .executeMethod((TokenMethodCall) t);
	    else {
		new UnexpectedDataTypeException(
			Zetta.getMethodReturnType((TokenMethodCall) t),
			Zetta.dtArray);
	    }
	} else if (t instanceof TokenSequence) {
	    final Variable v = new Variable(Zetta.dtArray, "", new Object[0]);
	    return Zetta.dtArray.operatorAssign(v, t);
	} else {
	    new UnexpectedTokenException(t, Zetta.tokenVariable,
		    Zetta.tokenMethodCall, Zetta.tokenSequence);
	}
	return null;
    }

    public static boolean tokenCanBeInteger(final Token t) {
	if (t instanceof TokenExpression) return true;
	else if (t instanceof TokenMethodCall) return true;
	else if (t instanceof TokenInteger) return true;
	else if (t instanceof TokenVariable) return true;
	return false;
    }

    public static boolean tokenCanBeString(final Token token) {
	if ((token instanceof TokenString)
		|| (token instanceof TokenMethodCall)
		|| (token instanceof TokenVariable)) return true;
	return false;
    }

}
