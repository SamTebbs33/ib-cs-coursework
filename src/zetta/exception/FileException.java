package zetta.exception;

import java.io.File;

import zetta.Zetta;
import zetta.lang.Variable;

/**
 * A file cannot be manipulated by a method, e.g. it doesn't exist or cannot be
 * written to or read from
 * 
 * @author samueltebbs
 * 
 */
public class FileException extends ZettaException {

    public FileException(final Variable file, final Exception e,
	    final EnumFileException efe) {
	this(file.dType.string(file), e, efe);
    }

    public FileException(final String file, final Exception e,
	    final EnumFileException efe) {
	super("\"" + file + "\"" + " (" + efe.str + ")", e);
	Zetta.exit();
    }

    public FileException(final File target, final Exception e,
	    final EnumFileException efe) {
	this(target.getName(), e, efe);
    }

    public FileException(final String fileName, final Exception e1) {
	super("\"" + fileName + "\"", e1);
    }

    public FileException(final Variable v, final Exception e) {
	this(v.dType.string(v), e);
    }

    public FileException(final File index, final EnumFileException notADir) {
	this(index, null, notADir);
    }

}
