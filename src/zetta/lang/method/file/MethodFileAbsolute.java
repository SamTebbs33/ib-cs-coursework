package zetta.lang.method.file;

import java.io.File;

import zetta.Zetta;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;

/**
 * 
 * @author samueltebbs, 22:43:10 - 21 Aug 2014
 */
public class MethodFileAbsolute extends MethodFileEscape {

    public MethodFileAbsolute(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the absolute path to the file" };
    }

    @Override
    public String[][] docThrows() {
	return new String[][] { {} };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable var = Zetta.getVariableOfType(object, Zetta.dtFile);
	final File file = (File) var.getIndex(DataTypeFile.INDEX_FILE);
	return asVariable(Zetta.dtString, file.getAbsolutePath());
    }

}
