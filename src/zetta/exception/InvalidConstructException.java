package zetta.exception;

/**
 * Construct doesn't exist
 * 
 * @author samueltebbs, 15:23:57 - 24 Jul 2014
 */
public class InvalidConstructException extends ZettaException {

    public InvalidConstructException(final String m) {
	super(m);
    }

    public InvalidConstructException() {}

    public InvalidConstructException(final String message, final Exception e) {
	super(message, e);
    }

}
