package zetta.lang.method.file;

import java.io.File;

import zetta.Zetta;
import zetta.exception.EnumFileException;
import zetta.exception.FileException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;

/**
 * 
 * @author samueltebbs, 21:38:45 - 21 Aug 2014
 */
public class MethodFileHasChild extends MethodFileWrite {

    public MethodFileHasChild(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns true if the file has a child with the same name as the argument" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtBoolean.name };
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtString.name } };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable var = Zetta.getVariableOfType(object, Zetta.dtFile);
	final File file = (File) var.getIndex(DataTypeFile.INDEX_FILE);
	if (file != null) {
	    if (file.isDirectory()) {
		final String fileName = Token.getString(args[0]);
		final File[] children = file.listFiles();
		for (final File f : children) {
		    if (f.getName().equals(fileName)) return asVariable(
			    Zetta.dtBoolean, true);
		}
		return asVariable(Zetta.dtBoolean, false);
	    } else {
		new FileException(file, EnumFileException.NOT_A_DIR);
	    }
	} else {
	    new FileException(file, EnumFileException.NO_FILE);
	}
	return asVariable(Zetta.dtBoolean, false);
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtBoolean;
    }

}
