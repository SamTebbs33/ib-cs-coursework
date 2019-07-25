package zetta.lang.method.string;

import zetta.Zetta;
import zetta.documentation.DocGenerator;
import zetta.exception.UnexpectedArgumentQuantityException;
import zetta.exception.UnexpectedDataTypeException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.method.MethodLibrary;
import zetta.token.TokenExpression;
import zetta.token.TokenMethodCall;
import zetta.token.TokenNumber;
import zetta.token.TokenOperator;
import zetta.token.TokenString;
import zetta.token.TokenVariable;

public class MethodStringEval extends MethodLibrary {

    public MethodStringEval(final String name) {
	super(name, Zetta.dtString.name);
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtString;
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { "Expression" } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns a String representation of the expression" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtString.name };
    }

    @Override
    public String[] docCodeExamples() {
	final String example = DocGenerator.HTML_CODE_OPEN
		+ "println(String.eval(\"hello \" + \"there\"));"
		+ DocGenerator.HTML_CODE_CLOSE + "<br><br>"
		+ "Prints \"hello there\"";
	return new String[] { example };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 1)) {
	    if (args[0] instanceof TokenExpression) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenExpression);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	if (args[0] instanceof TokenVariable) {
	    final Variable v = Zetta.getVariableOfType(object);
	    return asVariable(Zetta.dtString, v.dType.string(v));
	}
	if ((args[0].subTokens.length % 2) == 0) {
	    new UnexpectedArgumentQuantityException(args[0].subTokens.length, 3);
	    return null;
	}
	final Token first = args[0].subTokens[0];
	if (!((first instanceof TokenNumber)
		|| (first instanceof TokenMethodCall)
		|| (first instanceof TokenString) || (first instanceof TokenVariable))) {
	    new UnexpectedTokenException(first, Zetta.tokenNumber,
		    Zetta.tokenMethodCall, Zetta.tokenString,
		    Zetta.tokenVariable);
	    return null;
	}
	String string = "";
	final Token[] tokens = new Token[args[0].subTokens.length + 1];
	tokens[0] = new TokenOperator("+");
	for (int c = 1; c < tokens.length; c++) {
	    tokens[c] = args[0].subTokens[c - 1];
	}
	for (int c = 0; c < tokens.length; c += 2) {
	    String value = "";
	    final Token t1 = tokens[c], t2 = tokens[c + 1];
	    if (!((t1 instanceof TokenOperator) && t1.value.equals("+"))) {
		new UnexpectedTokenException(t1, Zetta.tokenOperator);
		System.exit(0);
	    }
	    if ((t2 instanceof TokenString) || (t2 instanceof TokenNumber)) {
		value = t2.value;
	    } else if (t2 instanceof TokenVariable) {
		final Variable v = Zetta.getVariableOfType(t2);
		value = v.dType.string(v);
	    } else if (t2 instanceof TokenMethodCall) {
		if (Zetta.getMethodReturnType((TokenMethodCall) t2) == Zetta.dtString) {
		    value = Zetta.executeMethod((TokenMethodCall) t2)
			    .toString();
		} else {
		    new UnexpectedDataTypeException(
			    Zetta.getMethodReturnType((TokenMethodCall) t2),
			    Zetta.dtString);
		}
	    }
	    string += value;
	}
	return asVariable(Zetta.dtString, string);
    }

}
