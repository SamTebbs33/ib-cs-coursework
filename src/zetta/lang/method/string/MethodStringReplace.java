package zetta.lang.method.string;

import zetta.Zetta;
import zetta.documentation.DocGenerator;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenNumber;
import zetta.token.TokenString;
import zetta.token.TokenVariable;

public class MethodStringReplace extends MethodStringContains {

    public MethodStringReplace(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtString.name, Zetta.dtString.name } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the a form of the String with all instances of the argument replaced with the second argument" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtString.name };
    }

    @Override
    public String[] docCodeExamples() {
	final String example = DocGenerator.HTML_CODE_OPEN
		+ Zetta.dtString.name + " $str(\"Hello, world!\");<br>"
		+ "println($str.replace(\"l\", \"abc\"));"
		+ DocGenerator.HTML_CODE_CLOSE + "<br><br>"
		+ "Prints \"Heabcabco, worabcd!\"";
	return new String[] { example };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 2)) {
	    if ((args[0] instanceof TokenString)
		    || (args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenNumber)) {
		if ((args[1] instanceof TokenString)
			|| (args[1] instanceof TokenVariable)
			|| (args[1] instanceof TokenNumber)) return true;
		else {
		    new UnexpectedTokenException(args[1], Zetta.tokenString,
			    Zetta.tokenVariable, Zetta.tokenNumber);
		}
	    } else {
		new UnexpectedTokenException(args[0], Zetta.tokenString,
			Zetta.tokenVariable, Zetta.tokenNumber);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable string = Zetta.getVariableOfType(object, Zetta.dtString);
	String value = "";
	if (args[0] instanceof TokenVariable) {
	    final Variable v = Zetta.getVariableOfType(args[0]);
	    value = v.dType.string(v);
	} else {
	    value = args[0].value;
	}
	String value2 = "";
	if (args[0] instanceof TokenVariable) {
	    final Variable v = Zetta.getVariableOfType(args[1]);
	    value2 = v.dType.string(v);
	} else {
	    value2 = args[1].value;
	}
	return asVariable(Zetta.dtString,
		string.dType.string(string).replace(value, value2));
    }

}
