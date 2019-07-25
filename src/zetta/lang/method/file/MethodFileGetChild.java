package zetta.lang.method.file;

import java.io.File;

import zetta.Zetta;
import zetta.exception.EnumFileException;
import zetta.exception.FileException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;
import zetta.token.TokenString;

/**
 * 
 * @author samueltebbs, 21:46:00 - 21 Aug 2014
 */
public class MethodFileGetChild extends MethodFileHasChild {

    public MethodFileGetChild(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns a File variable holding the child that has the same name as the argument" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtFile.name };
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtFile;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable var = Zetta.getVariableOfType(object, Zetta.dtFile);
	final File file = (File) var.getIndex(DataTypeFile.INDEX_FILE);
	if (file != null) {
	    if (file.isDirectory()) {
		final String fileName = Token.getString(args[0]);
		final File child = new File(file.getAbsolutePath()
			+ File.separator + fileName);
		if (child.exists()) return Zetta.dtFile.initVariable(
			"",
			new Token[] { new TokenString("d"
				+ child.getAbsolutePath() + "d") });
		else {
		    new FileException(child, EnumFileException.DOESNT_EXIST);
		}
	    } else {
		new FileException(file, EnumFileException.NOT_A_DIR);
	    }
	} else {
	    new FileException(file, EnumFileException.NO_FILE);
	}
	return null;
    }

}
