package zetta.lang.method.file;

import java.io.IOException;
import java.io.RandomAccessFile;

import zetta.Zetta;
import zetta.exception.FileException;
import zetta.lang.Variable;

/**
 * 
 * @author samueltebbs, 23:23:40 - 1 Sep 2014
 */
public class MethodFileRAFReadNextBool extends MethodFileRAFReadNext {

    public MethodFileRAFReadNextBool(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the next boolean value after the file pointer" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtBoolean.name };
    }

    @Override
    protected Variable read(final RandomAccessFile raf, final String fileName) {
	try {
	    return asVariable(Zetta.dtString, raf.readBoolean());
	} catch (final IOException e) {
	    new FileException(fileName, e);
	}
	return null;
    }

}
