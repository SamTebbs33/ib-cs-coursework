package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.method.MethodLibrary;

public class MethodMaths extends MethodLibrary {

    protected final String NUM_OR_INT = Zetta.dtNumber.name + " / "
	    + Zetta.dtInteger.name;

    public MethodMaths(final String name) {
	super(name, "Maths");
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { "" } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns " + name + "(argument)" };
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
    public DataType getReturnType() {
	return Zetta.dtInteger;
    }

}
