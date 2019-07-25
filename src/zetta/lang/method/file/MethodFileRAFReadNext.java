package zetta.lang.method.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import zetta.Zetta;
import zetta.exception.FileException;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;

/**
 * 
 * @author samueltebbs, 23:13:22 - 1 Sep 2014
 */
public class MethodFileRAFReadNext extends MethodFileClear {

    public MethodFileRAFReadNext(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the rest of the line at which the file pointer is" };
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtString.name } };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtFile);
	final File file = DataTypeFile.getFile(v);
	final RandomAccessFile raf = (RandomAccessFile) v
		.getIndex(DataTypeFile.INDEX_RAF);
	return read(raf, file.getName());
    }

    protected Variable read(final RandomAccessFile raf, final String fileName) {
	try {
	    return asVariable(Zetta.dtString, raf.readLine());
	} catch (final IOException e) {
	    new FileException(fileName, e);
	}
	return null;
    }

}
