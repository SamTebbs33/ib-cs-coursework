package zetta.exception;

import zetta.lang.DataType;

/**
 * 
 * @author samueltebbs, 07:23:06 - 11 Sep 2014
 */
public class CannotUseSubscriptForException extends ZettaException {

    public CannotUseSubscriptForException(final DataType m) {
	super(m.name);
    }

}
