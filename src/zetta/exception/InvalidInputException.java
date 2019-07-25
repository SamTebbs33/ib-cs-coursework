package zetta.exception;

import java.util.Arrays;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.token.TokenString;

/**
 * Invalid input received, e.g. string instead if integer
 * 
 * @author samueltebbs
 * 
 */
public class InvalidInputException extends ZettaException {

    public InvalidInputException(final DataType found,
	    final DataType... expected) {
	super("Found " + TokenString.string() + ", expected "
		+ Arrays.toString(expected));
	Zetta.exit();
    }

    public InvalidInputException() {
	super("");
    }

}
