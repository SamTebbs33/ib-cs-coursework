package zetta.lang.method.file;

import java.io.IOException;
import java.io.RandomAccessFile;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Token;
import zetta.token.TokenMethodCall;
import zetta.token.TokenNumber;
import zetta.token.TokenVariable;

/**
 * 
 * @author samueltebbs, 16:13:09 - 4 Sep 2014
 */
public class MethodFileRAFWriteNextInt extends MethodFileRAFWriteNext {

    public MethodFileRAFWriteNextInt(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtInteger.name } };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 1)) {
	    if ((args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenMethodCall)
		    || (args[0] instanceof TokenNumber)) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenVariable,
			Zetta.tokenMethodCall, Zetta.tokenNumber);
	    }
	}
	return false;
    }

    @Override
    protected void write(final RandomAccessFile raf, final Token token)
	    throws IOException {
	raf.writeBytes("" + Token.getInteger(token));
    }

}
