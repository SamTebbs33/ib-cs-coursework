package zetta.exception;

/**
 * The method has already been defined
 * 
 * @author samueltebbs
 * 
 */
public class MethodAlreadyDefinedException extends ZettaException {

    public MethodAlreadyDefinedException() {
	// TODO Auto-generated constructor stub
    }

    public MethodAlreadyDefinedException(final String message, final String d) {
	super(d + "." + message + "()");
	// TODO Auto-generated constructor stub
    }

}
