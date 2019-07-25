package zetta.exception;

import zetta.lang.DataType;
import zetta.lang.Variable;

/**
 * A different data type was expected
 * 
 * @author samueltebbs, 14:31:40 - 27 Jul 2014
 */
public class UnexpectedDataTypeException extends ZettaException {

    public UnexpectedDataTypeException(final Variable v,
	    final DataType... expected) {
	super("Found " + v.dType.name + ", expected " + format(expected));
	// TODO Auto-generated constructor stub
    }

    public UnexpectedDataTypeException(final DataType d, final DataType dType) {
	super("Found " + d.name + ", expected " + dType.name);
    }

    public UnexpectedDataTypeException(final String pointerDType,
	    final String name) {
	super("Found " + name + ", expected " + pointerDType);
    }

    public UnexpectedDataTypeException(final DataType d) {

    }

}
