package zetta.lang.method.array;

import java.util.Arrays;

import zetta.Zetta;
import zetta.exception.InvalidIndexException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.IterableDataType;
import zetta.token.TokenBooleanExpression;
import zetta.token.TokenPlaceholder;

public class MethodArraySet extends MethodArrayGet {

    public MethodArraySet(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtInteger.name, TYPE_GENERIC } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Sets the value at the index represented by argument 1" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtNull.name };
    }

    @Override
    public String[][] docThrows() {
	return new String[][] { { InvalidIndexException.class.getSimpleName() } };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 2)) {
	    if (Token.tokenCanBeNumber(args[0])) {
		if ((args[1] instanceof TokenPlaceholder)
			|| (args[1] instanceof TokenBooleanExpression)) return true;
		else {
		    new UnexpectedTokenException(args[1],
			    Zetta.tokenExpression, Zetta.tokenMethodCall,
			    Zetta.tokenNumber, Zetta.tokenString,
			    Zetta.tokenBooleanExpression);
		}
	    } else {
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
	final int index = Token.getInteger(args[0]);
	if (index > -1) {
	    if (index >= ((IterableDataType) v.dType).iteratorLength(v)) {
		final int length = ((IterableDataType) v.dType)
			.iteratorLength(v);
		v.setValue(Arrays.copyOf(v.getValue(), index + 2));
		for (int c = length; c < v.getValue().length; c++) {
		    v.setIndex(c + 1, new Variable(Zetta.dtNull, "",
			    new Object[0]));
		}
	    }
	    final Variable v2 = ((DataType) v.getIndex(0)).operatorAssign(
		    (Variable) v.getIndex(index + 1), args[1]);
	    if (v2 != null) {
		v.setIndex(index + 1, v2);
	    }
	    return null;
	} else {
	    new InvalidIndexException(index, 0,
		    ((IterableDataType) v.dType).iteratorLength(v));
	}
	return null;
    }
}
