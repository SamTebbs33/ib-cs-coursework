package zetta.exception;

import zetta.Zetta;

/**
 * Variable has already been declared
 * 
 * @author samueltebbs
 * 
 */
public class VariableAlreadyDefinedException extends ZettaException {

    public VariableAlreadyDefinedException(final String message) {
	super("$" + message);
	Zetta.exit();
    }

}
