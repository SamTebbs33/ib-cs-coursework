package zetta.exception;

/**
 * An incorrect index (arrays, goto) is used
 * 
 * @author samueltebbs
 * 
 */
public class InvalidIndexException extends ZettaException {

    public InvalidIndexException(final int index, final int min, final int max) {
	super("Index " + index + ", min " + min + ", max " + max);
    }

    public InvalidIndexException(final int index, final int max) {
	this(index, 0, max);
    }

    public InvalidIndexException(final int index, final long max) {
	super("Index " + index + ", min 0" + ", max " + max);
    }

}
