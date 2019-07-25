package zetta.lang.method.string;

import zetta.Zetta;
import zetta.documentation.DocGenerator;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenVariable;

/**
 * Zetta
 * 
 * @author samueltebbs, 18:34:13
 */
public class MethodStringConcat extends MethodStringEndsWith {

    public MethodStringConcat(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns this String with the argument added to the end" };
    }

    @Override
    public String[] docCodeExamples() {
	final String example = DocGenerator.HTML_CODE_OPEN
		+ Zetta.dtString.name + " $str(\"Hello\");<br>"
		+ "println($str.concat(\" everyone!\"));"
		+ DocGenerator.HTML_CODE_CLOSE + "<br><br>"
		+ "Prints \"Hello everyone!\"";
	return new String[] { example };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtString.name };
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
	return asVariable(Zetta.dtString, string.dType.string(string) + value);
    }

}
