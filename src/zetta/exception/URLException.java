package zetta.exception;

/**
 * 
 * @author samueltebbs, 19:28:30 - 23 Aug 2014
 */
public class URLException extends ZettaException {

    public URLException(final String m) {
	super(m);
    }

    public URLException() {}

    public URLException(final String m, final Exception e) {
	super(m, e);
    }

    public URLException(final String m, final EnumURLException ue,
	    final Exception e) {
	super(m + " (" + ue.strVal + ")", e);
    }

}
