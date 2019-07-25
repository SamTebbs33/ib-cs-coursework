package zetta.lang.method.system;

import zetta.Zetta;
import zetta.lang.DataType;

public class MethodPrint extends MethodPrintln {

    public MethodPrint(final String name) {
	super(name);
    }

    @Override
    protected String getPrintPrefix() {
	return "";
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtString;
    }

}
