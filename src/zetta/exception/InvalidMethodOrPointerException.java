package zetta.exception;

/**
 * Method or method+pointer combination doesn't exist
 * 
 * @author samueltebbs
 * 
 */
public class InvalidMethodOrPointerException extends ZettaException {

    public InvalidMethodOrPointerException(final String m) {
	super(m + "()");
    }

}
