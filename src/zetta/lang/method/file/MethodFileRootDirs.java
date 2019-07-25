package zetta.lang.method.file;

import java.io.File;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.method.MethodLibrary;
import zetta.token.TokenString;

/**
 * 
 * @author samueltebbs, 07:27:27 - 3 Sep 2014
 */
public class MethodFileRootDirs extends MethodLibrary {

    public MethodFileRootDirs(final String name) {
	super(name, Zetta.dtFile.name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns a File Array holding the system's root directories" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtArray.name + "<" + Zetta.dtFile.name
		+ ">" };
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
    public boolean canConstruct(final Token[] args) {
	return checkLength(args, true, 0);
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final File[] roots = File.listRoots();
	final Variable v = new Variable(Zetta.dtArray, "",
		new Object[roots.length + 1]);
	v.setIndex(0, Zetta.dtFile);
	for (int c = 0; c < roots.length; c++) {
	    v.setIndex(
		    c + 1,
		    Zetta.dtFile.initVariable("", new TokenString("d"
			    + roots[c].getAbsolutePath() + "d")));
	}
	return v;
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtArray;
    }

}
