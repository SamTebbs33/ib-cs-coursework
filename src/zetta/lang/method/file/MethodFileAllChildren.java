package zetta.lang.method.file;

import java.io.File;
import java.util.LinkedList;

import zetta.Zetta;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;
import zetta.token.TokenString;

/**
 * 
 * @author samueltebbs, 20:22:05 - 3 Sep 2014
 */
public class MethodFileAllChildren extends MethodFileChildren {

    LinkedList<Variable> allFiles = new LinkedList<Variable>();
    LinkedList<File> directories = new LinkedList<File>();

    public MethodFileAllChildren(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns a File Array containing every file within this file, even those in subdirectories." };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtArray.name + "<" + Zetta.dtFile.name
		+ ">" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtFile);
	final Variable array = new Variable(Zetta.dtArray, "");
	final File parent = DataTypeFile.getDir(v);
	final File[] children = parent.listFiles();
	for (final File f : children) {
	    if (f.isDirectory()) {
		directories.add(f);
	    }
	    allFiles.add(Zetta.dtFile.initVariable("",
		    new TokenString("d" + f.toString() + "d")));
	}
	for (int c = 0; c < directories.size(); c++) {
	    final File f = directories.get(c);
	    final File[] children2 = f.listFiles();
	    for (final File f2 : children2) {
		if (f2.isDirectory()) {// && !directories.contains(f2)){
		    directories.add(f2);
		}
		allFiles.add(Zetta.dtFile.initVariable("", new TokenString("d"
			+ f2.toString() + "d")));
	    }
	}
	array.setValue(new Object[allFiles.size() + 1]);
	array.setIndex(0, Zetta.dtFile);
	for (int c = 0; c < allFiles.size(); c++) {
	    array.setIndex(c + 1, allFiles.get(c));
	}
	return array;
    }

}
