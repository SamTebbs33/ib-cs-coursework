package zetta.exception;

/**
 * 
 * @author samueltebbs, 00:01:54 - 31 Jul 2014
 */
public class InvalidIconException extends ZettaException {

    public InvalidIconException(final String m) {
	super(m);
    }

    public InvalidIconException() {}

    public InvalidIconException(final String message, final Exception e) {
	super(message, e);
    }

}
