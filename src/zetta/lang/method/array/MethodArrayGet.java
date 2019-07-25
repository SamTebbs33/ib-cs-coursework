package zetta.lang.method.array;

import zetta.Zetta;
import zetta.exception.InvalidIndexException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.IterableDataType;
import zetta.token.TokenExpression;
import zetta.token.TokenMethodCall;
import zetta.token.TokenNumber;
import zetta.token.TokenVariable;

public class MethodArrayGet extends MethodArray {

    public MethodArrayGet(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtInteger.name } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the value held in index represented by the argument" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { TYPE_GENERIC };
    }

    @Override
    public String[][] docThrows() {
	return new String[][] { { InvalidIndexException.class.getSimpleName() } };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 1)) {
	    if (Token.tokenCanBeNumber(args[0])) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenExpression,
			Zetta.tokenMethodCall, Zetta.tokenNumber,
			Zetta.tokenVariable);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtArray);
	int index = 0;
	if (args[0] instanceof TokenVariable) {
	    final Variable v2 = Zetta.getVariableOfType(args[0],
		    Zetta.dtInteger);
	    index = (int) v2.getIndex(0);
	} else if (args[0] instanceof TokenNumber) {
	    if (args[0].value != null) {
		index = (int) Double.parseDouble(args[0].value.toString());
	    }
	} else if (args[0] instanceof TokenMethodCall) {
	    if (Zetta.getMethodReturnType((TokenMethodCall) args[0]) == Zetta.dtInteger) {
		final Variable v2 = ((TokenMethodCall) args[0]).method.execute(
			args[0].subTokens, null);
		if (v2 != null) {
		    index = (int) v2.getIndex(0);
		}
	    }
	} else if (args[0] instanceof TokenExpression) {
	    index = (int) Double.parseDouble(args[0].evaluate().toString());
	}

	if (index > -1) {
	    if (index < ((IterableDataType) v.dType).iteratorLength(v)) return asVariable(
		    (DataType) v.getIndex(0),
		    ((IterableDataType) v.dType).iteratorValue(v, index));
	    else {
		new InvalidIndexException(index, 0,
			((IterableDataType) v.dType).iteratorLength(v));
	    }
	} else {
	    new InvalidIndexException(index, 0,
		    ((IterableDataType) v).iteratorLength(v));
	}
	return null;
    }

    @Override
    public DataType getReturnType(final Variable pointer) {
	return (DataType) pointer.getIndex(0);
    }

}
