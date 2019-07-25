package zetta.exception;

/**
 * 
 * @author samueltebbs, 22:55:41 - 3 Sep 2014
 */
public class UnexpectedPointerException extends ZettaException {

    public UnexpectedPointerException(final String expected, final String found) {
	super("Found " + found + ", expected " + expected);
    }

}
