package zetta.lang.method.file;

import java.io.IOException;
import java.io.RandomAccessFile;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Token;
import zetta.token.TokenBooleanExpression;
import zetta.token.TokenMethodCall;
import zetta.token.TokenVariable;

/**
 * 
 * @author samueltebbs, 16:00:56 - 4 Sep 2014
 */
public class MethodFileRAFWriteNextBool extends MethodFileRAFWriteNext {

    public MethodFileRAFWriteNextBool(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtBoolean.name } };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 1)) {
	    if ((args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenMethodCall)
		    || (args[0] instanceof TokenBooleanExpression)) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenVariable,
			Zetta.tokenMethodCall, Zetta.tokenBooleanExpression);
	    }
	}
	return false;
    }

    @Override
    protected void write(final RandomAccessFile raf, final Token token)
	    throws IOException {
	raf.writeBoolean(Token.getBool(token));
    }

}
