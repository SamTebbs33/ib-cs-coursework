package zetta.lang.method.dictionary;

import zetta.Zetta;
import zetta.exception.InvalidIndexException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.IterableDataType;
import zetta.token.TokenMethodCall;
import zetta.token.TokenNumber;
import zetta.token.TokenVariable;

public class MethodDictionaryMerge extends MethodDictionary {

    public MethodDictionaryMerge(final String name) {
	super(name);
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 2)) {
	    if ((args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenNumber)
		    || (args[0] instanceof TokenMethodCall)) {
		if (args[1] instanceof TokenVariable) return true;
		else {
		    new UnexpectedTokenException(args[1], Zetta.tokenVariable,
			    Zetta.tokenNumber, Zetta.tokenString,
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
	index = Token.getInteger(args[0]);
	if ((index > -1)
		&& (index < ((IterableDataType) v.dType).iteratorLength(v))) {
	    index++;
	    if (args[1] instanceof TokenVariable) {
		final Variable v2 = Zetta.getVariableOfType(args[1],
			Zetta.dtArray);
		final Object[] result = new Object[((IterableDataType) v2.dType)
			.iteratorLength(v2)
			+ ((IterableDataType) v.dType).iteratorLength(v)];
		for (int c = 0; c < index; c++) {
		    result[c] = ((IterableDataType) v.dType)
			    .iteratorValue(v, c);

		}
		for (int c = 0; c < ((IterableDataType) v2.dType)
			.iteratorLength(v2); c++) {
		    result[c + index] = ((IterableDataType) v2.dType)
			    .iteratorValue(v2, c);
		}
		for (int c = 0; c < (((IterableDataType) v.dType)
			.iteratorLength(v) - index); c++) {
		    result[c + index
			    + ((IterableDataType) v2.dType).iteratorLength(v2)] = ((IterableDataType) v.dType)
			    .iteratorValue(v, c + index);
		}
		v.setValue(result);
	    }
	} else {
	    new InvalidIndexException(index, 0,
		    ((IterableDataType) v.dType).iteratorLength(v));
	}
	return null;
    }

}
