package zetta.lang.method.file;

import java.io.File;

import zetta.Zetta;
import zetta.exception.FileException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;

public class MethodFileExists extends MethodFileCreate {

    public MethodFileExists(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns true if this file exists" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtBoolean.name };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtFile);
	try {
	    // return true or false f.createNewFile();
	    return asVariable(Zetta.dtBoolean,
		    ((File) v.getIndex(DataTypeFile.INDEX_FILE)).exists());
	} catch (final Exception e) {
	    new FileException(v, e);
	}
	return null;
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtBoolean;
    }

}
