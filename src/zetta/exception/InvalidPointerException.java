package zetta.exception;

import zetta.Zetta;

/**
 * Pointer is invalid or not expected
 * 
 * @author samueltebbs
 * 
 */
public class InvalidPointerException extends ZettaException {

    public InvalidPointerException() {
	super("");
	Zetta.exit();
	// TODO Auto-generated constructor stub
    }

}
