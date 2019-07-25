package zetta.lang.method.file;

import java.io.File;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * 
 * @author samueltebbs, 10:00:34 - 13 Sep 2014
 */
public class MethodFileSeparator extends MethodFileUserDir {

    public MethodFileSeparator(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns system-dependant file path separator" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtString.name };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtString, File.separator);
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtString;
    }

}
