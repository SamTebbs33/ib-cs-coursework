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
 * @author samueltebbs, 22:28:10 - 11 Sep 2014
 */
public class MethodFileRAFSeekLine extends MethodFileRAFSeek {

    public MethodFileRAFSeekLine(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Moves the file pointer to the beginning of the line.\n If the file mode is APPEND and the argument is greater than the number of lines in the file, lines will be created until the argument is reached." };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable file = Zetta.getVariableOfType(object, Zetta.dtFile);
	final int dest = Token.getInteger(args[0]);
	final File f = DataTypeFile.getFile(file);
	final RandomAccessFile raf = DataTypeFile.getRAF(file);
	String line;
	int c = 0;
	int length = 0;
	if (dest < 0) {
	    new InvalidIndexException(dest, 0);
	}
	if ((int) file.getIndex(DataTypeFile.INDEX_MODE) == DataTypeFile.MODE_TRUNCATE) {
	    if (dest > f.length()) {
		for (int k = 0; k <= dest; k++) {
		    try {
			raf.writeUTF("\n");
			return null;
		    } catch (final IOException e) {
			new FileException(f.getName(), e);
		    }
		}
	    }
	}
	try {
	    while ((line = raf.readLine()) != null) {
		if (c == dest) {
		    raf.seek(length);
		    return null;
		}
		length += line.length() + 1;
		c++;
	    }
	    new InvalidIndexException(dest, f.length());
	} catch (final IOException e) {
	    new FileException(f.getName(), e);
	}
	return null;
    }

}
