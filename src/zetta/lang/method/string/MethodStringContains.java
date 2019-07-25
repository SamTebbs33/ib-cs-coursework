package zetta.lang.method.string;

import zetta.Zetta;
import zetta.documentation.DocGenerator;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.IterableDataType;
import zetta.token.TokenVariable;

public class MethodStringContains extends MethodStringEndsWith {

    public MethodStringContains(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtString.name } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns true if the String contains the argument" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtBoolean.name };
    }

    @Override
    public String[] docCodeExamples() {
	final String example = DocGenerator.HTML_CODE_OPEN
		+ Zetta.dtString.name + " $str(\"Hello, world!\");<br>"
		+ "println($str.contains(\"lo, wor\"));"
		+ DocGenerator.HTML_CODE_CLOSE + "<br><br>" + "Prints \"true\"";
	return new String[] { example };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable string = Zetta.getVariableOfType(object, Zetta.dtString);
	String value = "";
	if (args[0] instanceof TokenVariable) {
	    final Variable v = Zetta.getVariableOfType(args[0]);
	    if (v.dType instanceof IterableDataType) {
		for (int c = 0; c < ((IterableDataType) v.dType)
			.iteratorLength(v); c++) {
		    final Object o = ((IterableDataType) v.dType)
			    .iteratorValue(v, c);
		    if (!string.dType.string(string).contains(o.toString())) return asVariable(
			    Zetta.dtBoolean, false);
		}
		return asVariable(Zetta.dtBoolean, true);
	    }
	    value = v.dType.string(v);
	} else {
	    value = args[0].value;
	}
	return asVariable(Zetta.dtBoolean, string.dType.string(string)
		.contains(value));
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtBoolean;
    }

}
