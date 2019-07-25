package zetta.lang.method.string;

import zetta.Zetta;
import zetta.documentation.DocGenerator;
import zetta.lang.Token;
import zetta.lang.Variable;

public class MethodStringTrim extends MethodStringToLowerCase {

    public MethodStringTrim(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns a form of the String with leading and trailing spaces removed" };
    }

    @Override
    public String[] docCodeExamples() {
	final String example = DocGenerator.HTML_CODE_OPEN
		+ Zetta.dtString.name + " $str(\"  Hello, world!   \");<br>"
		+ "println($str.trim());" + DocGenerator.HTML_CODE_CLOSE
		+ "<br><br>" + "Prints \"Hello, world!\"";
	return new String[] { example };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable string = Zetta.getVariableOfType(object, Zetta.dtString);
	return asVariable(Zetta.dtString, string.dType.string(string).trim());
    }

}
