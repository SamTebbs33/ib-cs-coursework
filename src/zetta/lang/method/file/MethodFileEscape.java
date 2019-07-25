package zetta.lang.method.file;

import java.io.File;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;

/**
 * 
 * @author samueltebbs, 22:34:28 - 21 Aug 2014
 */
public class MethodFileEscape extends MethodFileRead {

    public MethodFileEscape(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the absolute path to the file with all spaces escaped" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtString.name };
    }

    @Override
    public String[][] docThrows() {
	return new String[][] { {} };
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtString;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable var = Zetta.getVariableOfType(object, Zetta.dtFile);
	final File file = (File) var.getIndex(DataTypeFile.INDEX_FILE);
	final String path = file.getAbsolutePath();
	String result = "";
	for (int c = 0; c < path.length(); c++) {
	    final char ch = path.charAt(c);
	    switch (ch) {
		case '\\':
		    c++;
		    break;
		case ' ':
		    result += "\\ ";
		    break;
		default:
		    result += String.valueOf(ch);
	    }
	}
	return asVariable(Zetta.dtString, result);
    }

}
