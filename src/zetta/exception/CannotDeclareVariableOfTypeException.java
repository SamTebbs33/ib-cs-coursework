package zetta.exception;

import zetta.lang.DataType;

/**
 * 
 * @author samueltebbs, 20:11:46 - 27 Aug 2014
 */
public class CannotDeclareVariableOfTypeException extends ZettaException {

    public CannotDeclareVariableOfTypeException(final DataType t) {
	super(t.name);
    }

}
