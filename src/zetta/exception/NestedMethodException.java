package zetta.exception;

/**
 * Attempting to declare a method inside another method
 * 
 * @author samueltebbs, 15:40:33 - 24 Jul 2014
 */
public class NestedMethodException extends ZettaException {

    public NestedMethodException(final String m) {
	super(m);
    }

    public NestedMethodException() {}

    public NestedMethodException(final String message, final Exception e) {
	super(message, e);
    }

}
