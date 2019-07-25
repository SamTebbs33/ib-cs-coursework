package zetta.lang.method.array;

import zetta.Zetta;
import zetta.exception.InvalidIndexException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * 
 * @author samueltebbs, 17:36:29 - 12 Sep 2014
 */
public class MethodArrayCopy extends MethodArray {

    public MethodArrayCopy(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtInteger.name },
		{ Zetta.dtInteger.name, Zetta.dtInteger.name } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] {
		"Returns a copy of this Array starting from the argument",
		"Returns a copy of this Array starting from argument 1 and ending at the argument 2" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtArray.name, Zetta.dtArray.name };
    }

    @Override
    public String[][] docThrows() {
	return new String[][] {
		{ InvalidIndexException.class.getSimpleName() },
		{ InvalidIndexException.class.getSimpleName() } };
    }

    @Override
    public String[] docCodeExamples() {
	return new String[] {};
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtArray;
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, false, 1)) {
	    if (Token.tokenCanBeNumber(args[0])) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenExpression,
			Zetta.tokenMethodCall, Zetta.tokenNumber,
			Zetta.tokenVariable);
	    }
	} else if (checkLength(args, true, 2)) {
	    if (Token.tokenCanBeNumber(args[0])) {
		if (Token.tokenCanBeNumber(args[1])) return true;
		else {
		    new UnexpectedTokenException(args[1],
			    Zetta.tokenExpression, Zetta.tokenMethodCall,
			    Zetta.tokenNumber, Zetta.tokenVariable);
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
	final Variable array = Zetta.getVariableOfType(object, Zetta.dtArray);
	final int arraySize = (int) array.dType.size(array);
	int from = 0, to = 0;
	final boolean toArg = args.length == 2;
	from = Token.getInteger(args[0]);
	to = Token.getInteger(args[1]);
	if (toArg && ((to < 0) || (to >= arraySize))) {
	    new InvalidIndexException(to, arraySize);
	}
	if (!toArg) {
	    to = arraySize - 1;
	}
	if ((from < 0) || (from >= arraySize)) {
	    new InvalidIndexException(from, arraySize);
	}
	final Variable result = new Variable(Zetta.dtArray, "",
		new Object[((to + 1) - from) + 1]);
	result.setIndex(0, array.getIndex(0));
	return result;
    }

}
