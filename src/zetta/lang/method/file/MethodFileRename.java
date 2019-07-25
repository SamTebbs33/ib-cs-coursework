package zetta.lang.method.file;

import java.io.File;

import zetta.Zetta;
import zetta.exception.FileException;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;
import zetta.token.TokenMethodCall;
import zetta.token.TokenVariable;

public class MethodFileRename extends MethodFileArchive {

    public MethodFileRename(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Renames the file to the argument" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtNull.name };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtFile);
	String value = "";
	if (args[0] instanceof TokenVariable) {
	    final Variable v2 = Zetta.getVariableOfType(args[0]);
	    value = v2.dType.string(v2);
	} else if (args[0] instanceof TokenMethodCall) {
	    value = ((TokenMethodCall) args[0]).method.execute(
		    args[0].subTokens, ((TokenMethodCall) args[0]).object)
		    .toString();
	} else {
	    value = args[0].value;
	}
	try {
	    ((File) v.getIndex(DataTypeFile.INDEX_FILE)).renameTo(new File(
		    value));
	} catch (final Exception e) {
	    new FileException(v, e);
	}
	return null;
    }

}
