package zetta.lang.method.file;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;

/**
 * 
 * @author samueltebbs, 20:07:50 - 30 Jul 2014
 */
public class MethodFileGetExtension extends MethodFileCreate {

    public MethodFileGetExtension(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the file's extension" };
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
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtFile);
	final String name = (String) v.getIndex(DataTypeFile.INDEX_NAME);
	if (name.contains(".") && (name.indexOf('.') != (name.length() - 1))) return asVariable(
		Zetta.dtString, name.substring(name.indexOf('.') + 1));
	return asVariable(Zetta.dtString, "");
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtString;
    }

}
