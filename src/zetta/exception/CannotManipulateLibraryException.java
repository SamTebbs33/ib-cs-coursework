package zetta.exception;

/**
 * The library .jar cannot be used, e.g. permission problems or incorrect
 * formatting
 * 
 * @author samueltebbs
 * 
 */
public class CannotManipulateLibraryException extends ZettaException {

    public CannotManipulateLibraryException(final String message,
	    final Exception e) {
	super(message, e);
	// TODO Auto-generated constructor stub
    }

}
