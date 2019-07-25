package zetta.exception;

import zetta.lang.Token;

/**
 * 
 * @author samueltebbs, 23:35:34 - 2 Sep 2014
 */
public class VoidReturnTypeException extends ZettaException {

    public VoidReturnTypeException(final Token t) {
	super(t.value);
    }

}
