package zetta.lang.method.string;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.method.MethodSpecific;

public class MethodString extends MethodSpecific {

    public MethodString(final String name) {
	super(name, Zetta.dtString.name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtString;
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtString.name };
    }

}
