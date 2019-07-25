package zetta.lang.method.string;

import zetta.Zetta;
import zetta.documentation.DocGenerator;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenMethodCall;
import zetta.token.TokenNumber;
import zetta.token.TokenString;
import zetta.token.TokenVariable;

public class MethodStringEndsWith extends MethodString {

    public MethodStringEndsWith(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtString.name } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns true if the String ends with the argument" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtBoolean.name };
    }

    @Override
    public String[] docCodeExamples() {
	final String example = DocGenerator.HTML_CODE_OPEN
		+ Zetta.dtString.name + " $str(\"Hello, world!\");<br>"
		+ "println($str.endsWith(\"world!\"));"
		+ DocGenerator.HTML_CODE_CLOSE + "<br><br>" + "Prints \"true\"";
	return new String[] { example };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 1)) {
	    if ((args[0] instanceof TokenString)
		    || (args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenNumber)
		    || (args[0] instanceof TokenMethodCall)) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenString,
			Zetta.tokenVariable, Zetta.tokenNumber);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable string = Zetta.getVariableOfType(object, Zetta.dtString);
	final String value = Token.getString(args[0]);
	return asVariable(Zetta.dtBoolean, string.dType.string(string)
		.endsWith(value));
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtBoolean;
    }

}
