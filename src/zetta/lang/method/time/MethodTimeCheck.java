package zetta.lang.method.time;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * Zetta
 * 
 * @author samueltebbs, 12:50:54 - 25 Jul 2014
 */
public class MethodTimeCheck extends MethodTime {

    public MethodTimeCheck(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { "" } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the amount of time that has passed since this variable was declared" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtInteger.name };
    }

    @Override
    public String[][] docThrows() {
	return new String[][] {};
    }

    @Override
    public String[] docCodeExamples() {
	return new String[] {};
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	return checkLength(args, true, 0);
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object);
	return asVariable(Zetta.dtInteger,
		System.currentTimeMillis() - Long.parseLong(v.dType.string(v)));
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtInteger;
    }

}
