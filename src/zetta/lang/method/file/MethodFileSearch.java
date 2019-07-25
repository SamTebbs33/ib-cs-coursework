package zetta.lang.method.file;

import java.io.File;
import java.util.LinkedList;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;
import zetta.token.TokenString;

/**
 * 
 * @author samueltebbs, 21:00:49 - 3 Sep 2014
 */
public class MethodFileSearch extends MethodFileHasChild {

    public MethodFileSearch(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Searches the file's children for a file with the same name as the argument and returns it, if not found, returns a variable of type null" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtFile.name };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable var = Zetta.getVariableOfType(object, Zetta.dtFile);
	final File parent = DataTypeFile.getDir(var);
	final String name = Token.getString(args[0]);
	final LinkedList<File> directories = new LinkedList<File>();
	final File[] children = parent.listFiles();
	for (final File f : children) {
	    if (f.getName().equals(name)) return Zetta.dtFile.initVariable("",
		    new TokenString("d" + f + "d"));
	    if (f.isDirectory()) {
		directories.add(f);
	    }
	}
	for (int c = 0; c < directories.size(); c++) {
	    final File f = directories.get(c);
	    final File[] files = f.listFiles();
	    for (final File f2 : files) {
		if (f2.getName().equals(name)) return Zetta.dtFile
			.initVariable("", new TokenString("d" + f2 + "d"));
		if (f2.isDirectory()) {
		    directories.add(f2);
		}
	    }
	}
	return new Variable(Zetta.dtNull, "");
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtFile;
    }

}
