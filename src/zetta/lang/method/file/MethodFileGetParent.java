package zetta.lang.method.file;

import java.io.File;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;
import zetta.token.TokenString;

/**
 * 
 * @author samueltebbs, 22:26:20 - 21 Aug 2014
 */
public class MethodFileGetParent extends MethodFileChildren {

    public MethodFileGetParent(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the file's parent directory" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtNull.name };
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { "" } };
    }

    @Override
    public String[][] docThrows() {
	return new String[][] { {} };
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtFile;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable var = Zetta.getVariableOfType(object, Zetta.dtFile);
	final File file = (File) var.getIndex(DataTypeFile.INDEX_FILE);
	final File parent = file.getParentFile();
	if (parent != null) return Zetta.dtFile.initVariable("",
		new Token[] { new TokenString("d" + parent.getAbsolutePath()
			+ "d") });
	else return Zetta.dtFile.initVariable("",
		new Token[] { new TokenString("dd") });
    }

}
