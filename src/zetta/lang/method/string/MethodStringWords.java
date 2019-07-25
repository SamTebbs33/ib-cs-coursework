package zetta.lang.method.string;

import zetta.Zetta;
import zetta.documentation.DocGenerator;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * 
 * @author samueltebbs, 19:19:05 - 9 Sep 2014
 */
public class MethodStringWords extends MethodStringTrim {

    public MethodStringWords(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns a String Array containing the different words in this string" };
    }

    @Override
    public String[] docCodeExamples() {
	final String example = DocGenerator.HTML_CODE_OPEN
		+ Zetta.dtString.name + " $str(\"Hello there everyone!\");<br>"
		+ "println($str.words());" + DocGenerator.HTML_CODE_CLOSE
		+ "<br><br>" + "Prints \"[Hello, there, everyone]\"";
	return new String[] { example };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtArray.name };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = new Variable(Zetta.dtArray, "", new Object[0]);
	final Variable string = Zetta.getVariableOfType(object, Zetta.dtString);
	final String[] words = string.dType.string(string).split(" ");
	v.setValue(new Object[words.length + 1]);
	v.setIndex(0, Zetta.dtString);
	for (int c = 0; c < words.length; c++) {
	    v.setIndex(c + 1, asVariable(Zetta.dtString, words[c]));
	}
	return v;
    }

}
