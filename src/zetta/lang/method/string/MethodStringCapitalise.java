package zetta.lang.method.string;

import zetta.Zetta;
import zetta.documentation.DocGenerator;
import zetta.lang.Token;
import zetta.lang.Variable;

public class MethodStringCapitalise extends MethodStringSwapCase {

    public MethodStringCapitalise(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns a form of the String that starts with a capital letter" };
    }

    @Override
    public String[] docCodeExamples() {
	final String example = DocGenerator.HTML_CODE_OPEN
		+ Zetta.dtString.name + " $str(\"hello, world!\");<br>"
		+ "println($str.capitalise());" + DocGenerator.HTML_CODE_CLOSE
		+ "<br><br>" + "Prints \"Hello, world!\"";
	return new String[] { example };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable string = Zetta.getVariableOfType(object, Zetta.dtString);
	String result = string.dType.string(string);
	if (result.length() > 0) {
	    char ch = result.charAt(0);
	    if ((ch <= 'z') && (ch >= 'a')) {
		ch = (char) (ch - ' ');
	    }
	    result = String.valueOf(ch) + result.substring(1);
	}
	return asVariable(Zetta.dtString, result);
    }

}
