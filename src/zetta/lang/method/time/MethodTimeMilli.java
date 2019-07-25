package zetta.lang.method.time;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.method.MethodLibrary;

/**
 * 
 * @author samueltebbs, 18:01:20 - 3 Aug 2014
 */
public class MethodTimeMilli extends MethodLibrary {

    public MethodTimeMilli(final String name) {
	super(name, Zetta.dtTime.name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { "" } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the system time in milliseconds" };
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
	return Method.checkLength(args, true, 0);
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtInteger, System.currentTimeMillis());
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtInteger;
    }

}
