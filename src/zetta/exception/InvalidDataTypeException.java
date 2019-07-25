package zetta.exception;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Variable;

/**
 * A different data type is expected
 * 
 * @author samueltebbs
 * 
 */
public class InvalidDataTypeException extends ZettaException {

    public InvalidDataTypeException(final Variable v,
	    final DataType... expected) {
	super("Found " + v.dType.name + ", expected " + format(expected));
	Zetta.exit();
	// TODO Auto-generated constructor stub
    }

    public InvalidDataTypeException(final String str) {
	super("Found "
		+ str
		+ ", expected "
		+ format(Zetta.dataTypes.toArray(new DataType[Zetta.dataTypes
			.size()])));
	Zetta.exit();
    }

}
