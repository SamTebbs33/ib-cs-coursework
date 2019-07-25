package zetta.lang.method.string;

import zetta.Zetta;
import zetta.documentation.DocGenerator;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenMethodCall;
import zetta.token.TokenNumber;
import zetta.token.TokenVariable;

/**
 * Zetta
 * 
 * @author samueltebbs, 19:51:05
 */
public class MethodStringChar extends MethodStringEval {

    public MethodStringChar(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtInteger.name } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the Unicode String value of the int" };
    }

    @Override
    public String[] docCodeExamples() {
	final String example = DocGenerator.HTML_CODE_OPEN
		+ "println(String.char(83));" + DocGenerator.HTML_CODE_CLOSE
		+ "<br><br>" + "Prints \"S\"";
	return new String[] { example };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 1)) {
	    if ((args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenNumber)
		    || (args[0] instanceof TokenMethodCall)) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenVariable,
			Zetta.tokenNumber, Zetta.tokenMethodCall);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final int value = Token.getInteger(args[0]);
	return asVariable(Zetta.dtString, String.valueOf((char) value));
    }

}
