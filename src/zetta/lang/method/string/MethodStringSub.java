package zetta.lang.method.string;

import zetta.Zetta;
import zetta.documentation.DocGenerator;
import zetta.exception.InvalidIndexException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenInteger;
import zetta.token.TokenMethodCall;
import zetta.token.TokenNumber;
import zetta.token.TokenVariable;

/**
 * @(#)MethodStringSub.java
 * 
 * 
 * @author
 * @version 1.00 2014/7/14
 */

public class MethodStringSub extends MethodString {

    public MethodStringSub(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtInteger.name, Zetta.dtInteger.name } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns a cut form of the String starting at argument 1 and ending at argument 2" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtString.name };
    }

    @Override
    public String[] docCodeExamples() {
	final String example = DocGenerator.HTML_CODE_OPEN
		+ Zetta.dtString.name + " $str(\"Hello, world!\");<br>"
		+ "println($str.sub(0, 5));" + DocGenerator.HTML_CODE_CLOSE
		+ "<br><br>" + "Prints \"Hello\"";
	return new String[] { example };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 2)) {
	    if ((args[0] instanceof TokenInteger)
		    || (args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenMethodCall)) {
		if ((args[1] instanceof TokenInteger)
			|| (args[1] instanceof TokenVariable)
			|| (args[1] instanceof TokenMethodCall)) return true;
		else {
		    new UnexpectedTokenException(args[1], Zetta.tokenInteger,
			    Zetta.tokenVariable, Zetta.tokenMethodCall);
		}
	    } else {
		new UnexpectedTokenException(args[0], Zetta.tokenInteger,
			Zetta.tokenVariable, Zetta.tokenMethodCall);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable obj = Zetta.getVariableOfType(object, Zetta.dtString);
	final int from = Token.getInteger(args[0]), to = Token
		.getInteger(args[1]);
	if ((from >= 0) && (from <= obj.dType.string(obj).length())) {
	    if ((to >= 0) && (to <= obj.dType.string(obj).length())) {
		if (from <= to) return asVariable(Zetta.dtString, obj.dType
			.string(obj).substring(from, to));
		else {
		    new InvalidIndexException(from, 0, to);
		}
	    } else {
		new InvalidIndexException(to, 0, (int) obj.dType.size(obj));
	    }
	} else {
	    new InvalidIndexException(from, 0, obj.dType.string(obj).length());
	}
	return null;
    }

}