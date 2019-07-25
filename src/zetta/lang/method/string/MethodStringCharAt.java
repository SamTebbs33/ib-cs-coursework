package zetta.lang.method.string;

import zetta.Zetta;
import zetta.documentation.DocGenerator;
import zetta.exception.InvalidIndexException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenExpression;
import zetta.token.TokenMethodCall;
import zetta.token.TokenNumber;
import zetta.token.TokenVariable;

public class MethodStringCharAt extends MethodStringEndsWith {

    public MethodStringCharAt(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtInteger.name } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the character at the index" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtString.name };
    }

    @Override
    public String[] docCodeExamples() {
	final String example = DocGenerator.HTML_CODE_OPEN
		+ Zetta.dtString.name + " $str(\"Hello, world!\");<br>"
		+ "println($str.charAt(7));" + DocGenerator.HTML_CODE_CLOSE
		+ "<br><br>" + "Prints \"w\"";
	return new String[] { example };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 1)) {
	    if ((args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenNumber)
		    || ((args[0] instanceof TokenMethodCall) || (args[0] instanceof TokenExpression))) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenVariable,
			Zetta.tokenNumber, Zetta.tokenMethodCall,
			Zetta.tokenExpression);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable string = Zetta.getVariableOfType(object, Zetta.dtString);
	final int value = Token.getInteger(args[0]);
	if ((value < string.dType.string(string).length()) && (value >= 0)) return asVariable(
		Zetta.dtString,
		String.valueOf(string.dType.string(string).charAt(value)));
	else {
	    new InvalidIndexException(value, 0, string.dType.string(string)
		    .length() - 1);
	}
	return null;
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtString;
    }

}
