package zetta.exception;

/**
 * A constructor has an invalid quantity of arguments
 * 
 * @author samueltebbs
 * 
 */
public class UnexpectedArgumentQuantityException extends ZettaException {

    public UnexpectedArgumentQuantityException(final int found,
	    final int expected) {
	super("Found " + found + ", expected " + expected);
    }

}
