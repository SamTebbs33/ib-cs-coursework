package zetta.lang.method.file;

import java.io.File;

import zetta.Zetta;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;

/**
 * 
 * @author samueltebbs, 23:07:16 - 7 Sep 2014
 */
public class MethodFileClearDir extends MethodFileClear {

    public MethodFileClearDir(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Deletes all subdirectories in this folder" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtFile);
	final File dir = DataTypeFile.getDir(v);
	deleteRecursive(dir);
	return null;
    }

    public static void deleteRecursive(final File path) {
	final File[] c = path.listFiles();
	for (final File file : c) {
	    if (file.isDirectory()) {
		deleteRecursive(file);
		file.delete();
	    } else {
		file.delete();
	    }
	}
    }

}
