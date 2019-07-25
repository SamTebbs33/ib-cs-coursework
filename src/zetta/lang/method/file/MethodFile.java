package zetta.lang.method.file;

import zetta.Zetta;
import zetta.exception.FileException;
import zetta.lang.method.MethodSpecific;

public class MethodFile extends MethodSpecific {

    public MethodFile(final String name) {
	super(name, Zetta.dtFile.name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[][] docThrows() {
	return new String[][] { { FileException.class.getSimpleName() } };
    }

}
