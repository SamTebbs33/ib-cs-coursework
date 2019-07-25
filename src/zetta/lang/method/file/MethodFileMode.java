package zetta.lang.method.file;

import zetta.Zetta;
import zetta.exception.EnumFileException;
import zetta.exception.FileException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;

/**
 * 
 * @author samueltebbs, 22:36:34 - 30 Aug 2014
 */
public class MethodFileMode extends MethodFile {

    public MethodFileMode(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Sets the file's mode" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtNull.name };
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtInteger.name } };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 1)) {
	    if (Token.tokenCanBeNumber(args[0])) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenExpression,
			Zetta.tokenNumber, Zetta.tokenMethodCall,
			Zetta.tokenVariable);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable file = Zetta.getVariableOfType(object, Zetta.dtFile);
	final int mode = Token.getInteger(args[0]);
	if ((mode >= 0) && (mode <= DataTypeFile.MAX_MODE)) {
	    file.setIndex(DataTypeFile.INDEX_MODE, mode);
	} else {
	    new FileException("" + mode, null, EnumFileException.NO_MODE);
	}

	return null;
    }

}
