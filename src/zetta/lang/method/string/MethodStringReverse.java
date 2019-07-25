package zetta.lang.method.string;

import zetta.Zetta;
import zetta.documentation.DocGenerator;
import zetta.lang.Token;
import zetta.lang.Variable;

public class MethodStringReverse extends MethodStringSwapCase {

    public MethodStringReverse(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns a form of the String with reversed characters" };
    }

    @Override
    public String[] docCodeExamples() {
	final String example = DocGenerator.HTML_CODE_OPEN
		+ Zetta.dtString.name + " $str(\"Hello, world!\");<br>"
		+ "println($str.reverse());" + DocGenerator.HTML_CODE_CLOSE
		+ "<br><br>" + "Prints \"!dlrow, olleH\"";
	return new String[] { example };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable string = Zetta.getVariableOfType(object, Zetta.dtString);
	String result = "";
	for (int c = string.dType.string(string).length() - 1; c >= 0; c--) {
	    result += string.dType.string(string).charAt(c);
	}
	return asVariable(Zetta.dtString, result);
    }

}
