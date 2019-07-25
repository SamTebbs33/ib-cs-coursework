package zetta.exception;

import zetta.Zetta;
import zetta.lang.Token;

/**
 * Unrecognised token
 * 
 * @author samueltebbs
 * 
 */
public class InvalidSyntaxException extends ZettaException {

    public InvalidSyntaxException(final String message) {
	super(message);
	Zetta.exit(); // In order to stop null pointer exceptions on invalid
		      // tokens.
	// TODO Auto-generated constructor stub
    }

    public InvalidSyntaxException(final Token token) {
	super("Cannot run "
		+ token.toString().substring(
			token.toString().lastIndexOf('.') + 1,
			token.toString().indexOf('@')));
	// TODO Auto-generated constructor stub
    }

}
