package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * 
 * @author samueltebbs, 18:02:18 - 4 Sep 2014
 */
public class MethodMathsCyclicAddition extends MethodMathsInRange {

    public MethodMathsCyclicAddition(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { NUM_OR_INT, NUM_OR_INT, NUM_OR_INT } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "If arg1 + arg4 is less than arg2, returns arg3. If args1 + args4 is greater than arg3, returns arg2" };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 4)) {
	    if (Token.tokenCanBeNumber(args[0])) {
		if (Token.tokenCanBeNumber(args[1])) {
		    if (Token.tokenCanBeNumber(args[2])) {
			if (Token.tokenCanBeNumber(args[3])) return true;
			else {
			    new UnexpectedTokenException(args[3],
				    Zetta.tokenNumber, Zetta.tokenMethodCall,
				    Zetta.tokenVariable, Zetta.tokenExpression);
			}
		    } else {
			new UnexpectedTokenException(args[2],
				Zetta.tokenNumber, Zetta.tokenMethodCall,
				Zetta.tokenVariable, Zetta.tokenExpression);
		    }
		} else {
		    new UnexpectedTokenException(args[1], Zetta.tokenNumber,
			    Zetta.tokenMethodCall, Zetta.tokenVariable,
			    Zetta.tokenExpression);
		}
	    } else {
		new UnexpectedTokenException(args[0], Zetta.tokenNumber,
			Zetta.tokenMethodCall, Zetta.tokenVariable,
			Zetta.tokenExpression);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final double[] a = { Token.getDouble(args[0]),
		Token.getDouble(args[1]), Token.getDouble(args[2]),
		Token.getDouble(args[3]) };
	double val = a[0];
	final double min = a[1], max = a[2], inc = a[3];
	if ((val + inc) < min) {
	    val = max;
	} else if ((val + inc) > max) {
	    val = min;
	}
	return asVariable(Zetta.dtNumber, val);
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtNumber;
    }

}
