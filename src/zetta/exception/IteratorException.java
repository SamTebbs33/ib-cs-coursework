package zetta.exception;

import zetta.lang.DataType;

/**
 * 
 * @author samueltebbs, 01:42:07 - 23 Aug 2014
 */
public class IteratorException extends ZettaException {

    public IteratorException(final DataType m) {
	super("Cannot iterate over " + m.name);
    }

}
