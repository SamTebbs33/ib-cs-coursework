package zetta.exception;

import zetta.lang.Token;

/**
 * 
 * @author samueltebbs, 10:51:13 - 22 Aug 2014
 */
public class CannotRunTokenException extends ZettaException {

    public CannotRunTokenException(final Token t) {
	super(UnexpectedTokenException.format(new Token[] { t }));
    }

}
