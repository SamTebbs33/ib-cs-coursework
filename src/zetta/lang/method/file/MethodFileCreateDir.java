package zetta.lang.method.file;

import java.io.File;

import zetta.Zetta;
import zetta.exception.FileException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;

public class MethodFileCreateDir extends MethodFileCreate {

    public MethodFileCreateDir(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtFile);
	try {
	    final File f = DataTypeFile.deleteFileIfMode(v);
	    f.mkdir();
	    return null;
	} catch (final Exception e) {
	    new FileException(v, e);
	}
	return null;
    }

    @Override
    public DataType getReturnType() {
	return null;
    }

}
