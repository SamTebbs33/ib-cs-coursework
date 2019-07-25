package zetta.lang.method.file;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenString;

/**
 * 
 * @author samueltebbs, 07:40:05 - 3 Sep 2014
 */
public class MethodFileUserDir extends MethodFileRootDirs {

    public MethodFileUserDir(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the current user's root folder" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtFile.name };
    }

    @Override
    public String[][] docThrows() {
	return new String[][] { {} };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = new Variable(Zetta.dtArray, "", new Object[2]);
	v.setIndex(0, Zetta.dtFile);
	v.setIndex(
		1,
		Zetta.dtFile.initVariable("",
			new TokenString("d" + System.getProperty("user.home")
				+ "d")));
	return v;
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtFile;
    }

}
