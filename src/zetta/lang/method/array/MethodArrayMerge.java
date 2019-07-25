package zetta.lang.method.array;

import zetta.Zetta;
import zetta.exception.InvalidIndexException;
import zetta.exception.UnexpectedDataTypeException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeArray;
import zetta.lang.datatype.IterableDataType;
import zetta.token.TokenMethodCall;
import zetta.token.TokenNumber;
import zetta.token.TokenVariable;

public class MethodArrayMerge extends MethodArray {

    public MethodArrayMerge(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtInteger.name, Zetta.dtArray.name } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns this array merged with this array starting at the index represented by argument 1" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtArray.name };
    }

    @Override
    public String[][] docThrows() {
	return new String[][] { { InvalidIndexException.class.getSimpleName() } };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 2)) {
	    if ((args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenNumber)
		    || (args[0] instanceof TokenMethodCall)) {
		if ((args[1] instanceof TokenVariable)
			|| (args[1] instanceof TokenMethodCall)) return true;
		else {
		    new UnexpectedTokenException(args[1], Zetta.tokenVariable,
			    Zetta.tokenMethodCall);
		}
	    } else {
		new UnexpectedTokenException(args[0], Zetta.tokenVariable,
			Zetta.tokenNumber);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtArray);
	int index = 0;
	final Variable res = new Variable(Zetta.dtArray, "", new Object[0]);
	Variable v2 = null;
	index = Token.getInteger(args[0]);
	if ((index > -1)
		&& (index < ((IterableDataType) v.dType).iteratorLength(v))) {
	    index++;
	    if (args[1] instanceof TokenVariable) {
		v2 = Zetta.getVariableOfType(args[1], Zetta.dtArray);
	    } else {
		if (Zetta.getMethodReturnType((TokenMethodCall) args[1]) instanceof DataTypeArray) {
		    v2 = Zetta.executeMethod((TokenMethodCall) args[1]);
		} else {
		    new UnexpectedDataTypeException(
			    Zetta.getMethodReturnType((TokenMethodCall) args[1]),
			    Zetta.dtArray);
		}
	    }
	    final Object[] result = new Object[((IterableDataType) v2.dType)
		    .iteratorLength(v2)
		    + ((IterableDataType) v.dType).iteratorLength(v) + 1];
	    for (int c = 0; c < index; c++) {
		result[c + 1] = ((IterableDataType) v.dType)
			.iteratorValue(v, c);

	    }
	    for (int c = 0; c < ((IterableDataType) v2.dType)
		    .iteratorLength(v2); c++) {
		result[c + index + 1] = ((IterableDataType) v2.dType)
			.iteratorValue(v2, c);
	    }
	    for (int c = 0; c < (((IterableDataType) v.dType).iteratorLength(v) - index); c++) {
		result[c + index
			+ ((IterableDataType) v2.dType).iteratorLength(v2) + 1] = ((IterableDataType) v.dType)
			.iteratorValue(v, c + index);
	    }
	    res.setValue(result);
	    return res;
	} else {
	    new InvalidIndexException(index, 0,
		    ((IterableDataType) v.dType).iteratorLength(v));
	}
	return null;
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtArray;
    }

}
