package zetta.lang.method.file;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import zetta.Zetta;
import zetta.exception.InterpreterErrorException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;

/**
 * 
 * @author samueltebbs, 14:22:25 - 3 Sep 2014
 */
public class MethodFileDateModified extends MethodFileClear {

    public MethodFileDateModified(final String name) {
	super(name);
    }

    @Override
    public String[][] docThrows() {
	return new String[][] { {} };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the date when this file was last modified" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final File f = DataTypeFile.getFile(Zetta.getVariableOfType(object,
		Zetta.dtFile));
	final SimpleDateFormat df = new SimpleDateFormat(
		"dd-MM-yyyy hh:mm:ss zzz");
	try {
	    return asVariable(Zetta.dtString,
		    df.parse(df.format(new Date(f.lastModified()))));
	} catch (final ParseException e) {
	    new InterpreterErrorException(e);
	}
	return null;
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtString;
    }

}
