package zetta.exception;

/**
 * 
 * @author samueltebbs, 20:35:21 - 30 Jul 2014
 */
public enum EnumFileException {

    NOT_A_DIR("Not a directory"), IS_A_DIR("Is a directory"), NO_FILE(
	    "No file registered by declaration"), DOESNT_EXIST("Doesn't exist"), NO_FORMAT(
	    "No such format"), NO_MODE("No such mode");

    public String str;

    EnumFileException(final String str) {
	this.str = str;
    }

}
