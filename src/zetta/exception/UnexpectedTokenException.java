package zetta.exception;

import zetta.lang.Token;

/**
 * A different token was expected
 * 
 * @author samueltebbs
 * 
 */
public class UnexpectedTokenException extends ZettaException {

    public UnexpectedTokenException(final Token found, final Token... expected) {
	super("Found " + found + ", expected " + format(expected));
    }

    public static String format(final Token[] a) {
	String res = "[";
	if (a != null) {
	    for (int c = 0; c < a.length; c++) {
		if (a[c].toString().contains(".")
			&& a[c].toString().contains("@")) {
		    res += a[c].toString().substring(
			    a[c].toString().lastIndexOf('.') + 1,
			    a[c].toString().lastIndexOf('@'));
		} else {
		    res += a[c];
		}
		if (c < (a.length - 1)) {
		    res += " | ";
		}
	    }
	    res += "]";
	}
	return res;
    }

}
