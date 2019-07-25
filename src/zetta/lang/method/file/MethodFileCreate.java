package zetta.lang.method.file;

/**
 * @(#)MethodCreate.java
 *
 *
 * @author 
 * @version 1.00 2014/7/1
 */

import java.io.File;

import zetta.Zetta;
import zetta.exception.FileException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;

public class MethodFileCreate extends MethodFile {

    public MethodFileCreate(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { "" } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Creates this file" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtNull.name };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 0)) return true;
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtFile);
	try {
	    final File f = DataTypeFile.deleteFileIfMode(v);
	    f.createNewFile();
	    return null;
	} catch (final Exception e) {
	    new FileException(v, e);
	}
	return null;
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtNull;
    }
}