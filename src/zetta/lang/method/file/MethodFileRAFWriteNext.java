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
 * @author samueltebbs, 15:47:00 - 4 Sep 2014
 */
public class MethodFileRAFWriteNext extends MethodFileWrite {

    public MethodFileRAFWriteNext(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Writes the argument to the file pointer" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtNull.name };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtFile);
	final File f = DataTypeFile.getFile(v);
	final RandomAccessFile raf = (RandomAccessFile) v
		.getIndex(DataTypeFile.INDEX_RAF);
	try {
	    write(raf, args[0]);
	} catch (final IOException e) {
	    new FileException(f, e, null);
	}
	return null;
    }

    protected void write(final RandomAccessFile raf, final Token token)
	    throws IOException {
	raf.writeBytes(Token.getString(token));
    }

}
