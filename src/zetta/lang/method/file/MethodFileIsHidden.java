package zetta.lang.method.file;

import java.io.File;

import zetta.Zetta;
import zetta.exception.EnumFileException;
import zetta.exception.FileException;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;

/**
 * 
 * @author samueltebbs, 14:56:46 - 3 Sep 2014
 */
public class MethodFileIsHidden extends MethodFileExists {

    public MethodFileIsHidden(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns true if the file is hidden" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtFile);
	final File f = (File) v.getIndex(DataTypeFile.INDEX_FILE);
	if (f.exists()) {
	    try {
		// return true or false f.createNewFile();
		return asVariable(Zetta.dtBoolean, f.isHidden());
	    } catch (final Exception e) {
		new FileException(v, e);
	    }
	    new FileException(f, EnumFileException.DOESNT_EXIST);
	}
	return null;
    }

}
