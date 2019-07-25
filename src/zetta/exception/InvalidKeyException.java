package zetta.exception;

/**
 * The desired key in a dictionary etc. hasn't been set
 * 
 * @author samueltebbs
 */
public class InvalidKeyException extends ZettaException {

    public InvalidKeyException(final String message) {
	super("\"" + message + "\"");
	// TODO Auto-generated constructor stub
    }

}
