package zetta.lang.method.file;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenString;

/**
 * 
 * @author samueltebbs, 07:43:31 - 3 Sep 2014
 */
public class MethodFileScriptDir extends MethodFileUserDir {

    public MethodFileScriptDir(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the directory in which Zetta is running from" };
    }

    @Override
    public String[][] docThrows() {
	return new String[][] { {} };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return Zetta.dtFile.initVariable("",
		new TokenString("d" + System.getProperty("user.dir") + "d"));
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtFile;
    }

}
