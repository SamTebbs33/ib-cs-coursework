package zetta.exception;

/**
 * The desired method has not been defined
 * 
 * @author samueltebbs, 14:59:10 - 24 Jul 2014
 */
public class MethodUndefinedException extends ZettaException {

    public MethodUndefinedException(final String m) {
	super(m + "()");
    }

    public MethodUndefinedException() {}

    public MethodUndefinedException(final String message, final Exception e) {
	super(message, e);
    }

}
