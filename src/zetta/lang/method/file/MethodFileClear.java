package zetta.lang.method.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import zetta.Zetta;
import zetta.exception.EnumFileException;
import zetta.exception.FileException;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;

public class MethodFileClear extends MethodFileCreate {

    public MethodFileClear(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Clears the file's contents" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtFile);
	final File f = DataTypeFile.getFile(v);
	try {
	    new PrintWriter(f);
	} catch (final FileNotFoundException e) {
	    new FileException(f, e, EnumFileException.DOESNT_EXIST);
	}
	return null;
    }

}
