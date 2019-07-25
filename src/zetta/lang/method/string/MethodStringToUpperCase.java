package zetta.lang.method.string;

import zetta.Zetta;
import zetta.documentation.DocGenerator;
import zetta.lang.Token;
import zetta.lang.Variable;

public class MethodStringToUpperCase extends MethodStringToLowerCase {

    public MethodStringToUpperCase(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns a form of the String with upper case letters" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable string = Zetta.getVariableOfType(object, Zetta.dtString);
	return asVariable(Zetta.dtString, string.dType.string(string)
		.toUpperCase());
    }

    @Override
    public String[] docCodeExamples() {
	final String example = DocGenerator.HTML_CODE_OPEN
		+ Zetta.dtString.name + " $str(\"Hello, world!\");<br>"
		+ "println($str.toUpperCase());" + DocGenerator.HTML_CODE_CLOSE
		+ "<br><br>" + "Prints \"HELLO, WORLD!\"";
	return new String[] { example };
    }

}
