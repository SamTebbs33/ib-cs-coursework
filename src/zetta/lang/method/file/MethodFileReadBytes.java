package zetta.lang.method.file;

import java.io.File;
import java.io.FileInputStream;

import zetta.Zetta;
import zetta.exception.EnumFileException;
import zetta.exception.FileException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;

/**
 * 
 * @author samueltebbs, 12:54:07 - 22 Aug 2014
 */
public class MethodFileReadBytes extends MethodFileRead {

    public MethodFileReadBytes(final String name) {
	super(name);
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtArray.name + "<" + Zetta.dtInteger.name
		+ ">" };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns an " + Zetta.dtInteger
		+ " Array containing the bytes in the file" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtFile);
	final Variable array = new Variable(Zetta.dtArray, "", new Object[0]);
	if (!((File) v.getIndex(DataTypeFile.INDEX_FILE)).isDirectory()) {
	    try {
		final FileInputStream fis = new FileInputStream(
			(File) v.getIndex(DataTypeFile.INDEX_FILE));
		final long length = ((File) v.getIndex(DataTypeFile.INDEX_FILE))
			.length();
		array.setValue(new Object[(int) (length + 1)]);
		for (int c = 0; c < length; c++) {
		    array.setIndex(c + 1, fis.read());
		}
		fis.close();
		array.setIndex(0, Zetta.dtInteger);
		return array;
	    } catch (final Exception e) {
		new FileException(v, e);
	    }
	} else {
	    new FileException((File) v.getIndex(DataTypeFile.INDEX_FILE),
		    EnumFileException.IS_A_DIR);
	}
	return null;
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtArray;
    }

}
