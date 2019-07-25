package zetta.exception;

/**
 * 
 * @author samueltebbs, 23:52:21 - 11 Sep 2014
 */
public enum EnumURLException {

    NO_CONNECTION("Cannot connect");

    String strVal;

    EnumURLException(final String str) {
	strVal = str;
    }

}
