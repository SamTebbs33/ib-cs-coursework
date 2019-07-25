package zetta.lang.method.string;

import zetta.Zetta;
import zetta.documentation.DocGenerator;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenVariable;

public class MethodStringStartsWith extends MethodStringEndsWith {

    public MethodStringStartsWith(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns true if the String starts with the argument" };
    }

    @Override
    public String[] docCodeExamples() {
	final String example = DocGenerator.HTML_CODE_OPEN
		+ Zetta.dtString.name + " $str(\"Hello, world!\");<br>"
		+ "println($str.startsWith(\"Hello\"));"
		+ DocGenerator.HTML_CODE_CLOSE + "<br><br>" + "Prints \"true\"";
	return new String[] { example };
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
	return asVariable(Zetta.dtString, string.dType.string(string)
		.startsWith(value));
    }

}
