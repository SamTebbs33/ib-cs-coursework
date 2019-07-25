package zetta.lang.method.string;

import zetta.Zetta;
import zetta.documentation.DocGenerator;
import zetta.lang.Token;
import zetta.lang.Variable;

public class MethodStringSwapCase extends MethodStringToLowerCase {

    public MethodStringSwapCase(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns a form of the String with swapped case" };
    }

    @Override
    public String[] docCodeExamples() {
	final String example = DocGenerator.HTML_CODE_OPEN
		+ Zetta.dtString.name + " $str(\"Hello, world!\");<br>"
		+ "println($str.swapCase());" + DocGenerator.HTML_CODE_CLOSE
		+ "<br><br>" + "Prints \"hELLO, WORLD!\"";
	return new String[] { example };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable string = Zetta.getVariableOfType(object, Zetta.dtString);
	String result = "";
	for (char ch : string.dType.string(string).toCharArray()) {
	    if ((ch <= 'z') && (ch >= 'a')) {
		ch -= ' ';
		result += String.valueOf(ch);
	    } else if ((ch <= 'Z') && (ch >= 'A')) {
		ch += ' ';
		result += String.valueOf(ch);
	    } else {
		result += String.valueOf(ch);
	    }
	}
	return asVariable(Zetta.dtString, result);
    }

}
