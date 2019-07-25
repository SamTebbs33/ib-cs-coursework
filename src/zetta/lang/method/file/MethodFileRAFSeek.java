package zetta.lang.method.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import zetta.Zetta;
import zetta.exception.FileException;
import zetta.exception.InvalidIndexException;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;

/**
 * 
 * @author samueltebbs, 16:58:44 - 1 Sep 2014
 */
public class MethodFileRAFSeek extends MethodFileMode {

    public MethodFileRAFSeek(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Moves the file pointer to the argument" };
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtInteger.name } };
    }

    @Override
    public String[][] docThrows() {
	return new String[][] { { FileException.class.getSimpleName(),
		InvalidIndexException.class.getSimpleName() } };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable file = Zetta.getVariableOfType(object, Zetta.dtFile);
	final int dest = Token.getInteger(args[0]);
	File f;
	if ((f = DataTypeFile.getFile(file)) != null) {
	    if ((dest > -1) && (dest <= f.length())) {
		try {
		    ((RandomAccessFile) file.getIndex(DataTypeFile.INDEX_RAF))
			    .seek(dest);
		} catch (final IOException e) {
		    new FileException(f.getName(), e);
		}
	    } else {
		new InvalidIndexException(dest, 0, (int) f.length());
	    }
	}
	return null;
    }

}
