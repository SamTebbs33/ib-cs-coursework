package zetta.exception;

import zetta.Zetta;

/**
 * Variable hasn't been declared in current scope
 * 
 * @author samueltebbs
 * 
 */
public class UndefinedVariableException extends ZettaException {

    public UndefinedVariableException(final String message) {
	super("\"$" + message + "\"");
	Zetta.exit();
	// TODO Auto-generated constructor stub
    }

}
