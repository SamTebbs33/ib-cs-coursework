package zetta.lang.method.string;

import zetta.Zetta;
import zetta.documentation.DocGenerator;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * 
 * @author samueltebbs, 10:37:32 - 15 Aug 2014
 */
public class MethodStringSplit extends MethodStringContains {

    public MethodStringSplit(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Splits the String by the argument and returns a String Array containing the results" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtArray.name };
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtArray;
    }

    @Override
    public String[] docCodeExamples() {
	final String example = DocGenerator.HTML_CODE_OPEN
		+ Zetta.dtString.name + " $str(\"Hello, world!\");<br>"
		+ "println($str.split(\"o\"));" + DocGenerator.HTML_CODE_CLOSE
		+ "<br><br>" + "Prints \"[hell, w, rld!]\"";
	return new String[] { example };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable string = Zetta.getVariableOfType(object, Zetta.dtString);
	final String delimiter = Token.getString(args[0]);
	final String[] strings = ((String) string.getIndex(0)).split(delimiter);
	final Object[] array = new Object[strings.length + 1];
	array[0] = Zetta.dtString;
	for (int c = 0; c < strings.length; c++) {
	    array[c + 1] = asVariable(Zetta.dtString, strings[c]);
	}
	return new Variable(Zetta.dtArray, "", array);
    }

}
