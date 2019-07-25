package zetta.lang.method.file;

import java.io.File;

import zetta.Zetta;
import zetta.exception.FileException;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;

public class MethodFileDelete extends MethodFileCreate {

    public MethodFileDelete(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v2 = Zetta.getVariableOfType(object, Zetta.dtFile);
	try {
	    final File f = ((File) v2.getIndex(DataTypeFile.INDEX_FILE));
	    if (f.isDirectory()) {
		deleteRecursive(f);
	    } else {
		f.delete();
	    }
	} catch (final Exception e) {
	    new FileException(v2, e);
	}
	return null;
    }

    public void deleteRecursive(final File path) {
	final File[] c = path.listFiles();
	for (final File file : c) {
	    if (file.isDirectory()) {
		deleteRecursive(file);
		file.delete();
	    } else {
		file.delete();
	    }
	}
	path.delete();
    }

}
