package zetta.lang.method.file;

import java.io.IOException;
import java.io.RandomAccessFile;

import zetta.Zetta;
import zetta.exception.FileException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;

/**
 * 
 * @author samueltebbs, 17:21:06 - 1 Sep 2014
 */
public class MethodFileRAFPointer extends MethodFileClear {

    public MethodFileRAFPointer(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the file pointer at which certain methods will write to" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtInteger.name };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable file = Zetta.getVariableOfType(object, Zetta.dtFile);
	try {
	    return asVariable(Zetta.dtInteger,
		    ((RandomAccessFile) file.getIndex(DataTypeFile.INDEX_RAF))
			    .getFilePointer());
	} catch (final IOException e) {
	    new FileException(file, e);
	}
	return null;
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtInteger;
    }

}
