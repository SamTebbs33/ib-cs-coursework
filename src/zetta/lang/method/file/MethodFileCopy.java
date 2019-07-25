package zetta.lang.method.file;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenString;
import zetta.token.TokenVariable;

public class MethodFileCopy extends MethodFile {

    public MethodFileCopy(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Copies the file to the destination represented by the argument" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtNull.name };
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtString.name } };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 1)) {
	    if ((args[0] instanceof TokenString)
		    || (args[0] instanceof TokenVariable)) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenString,
			Zetta.tokenVariable);
	    }
	}
	return false;
    }

    // TODO: Add functionality
    @Override
    public Variable execute(final Token[] args, final Token object) {
	return null;
    }

}
