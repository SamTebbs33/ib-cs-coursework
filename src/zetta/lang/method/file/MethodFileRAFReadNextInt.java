package zetta.lang.method.file;

import java.io.IOException;
import java.io.RandomAccessFile;

import zetta.Zetta;
import zetta.exception.FileException;
import zetta.lang.Variable;

/**
 * 
 * @author samueltebbs, 23:20:26 - 1 Sep 2014
 */
public class MethodFileRAFReadNextInt extends MethodFileRAFReadNext {

    public MethodFileRAFReadNextInt(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the next number after the file pointer" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtInteger.name };
    }

    @Override
    protected Variable read(final RandomAccessFile raf, final String fileName) {
	try {
	    return asVariable(Zetta.dtInteger, raf.readInt());
	} catch (final IOException e) {
	    new FileException(fileName, e);
	}
	return null;
    }

}
