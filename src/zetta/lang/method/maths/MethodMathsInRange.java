package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * 
 * @author samueltebbs, 11:27:21 - 20 Aug 2014
 */
public class MethodMathsInRange extends MethodMaths {

    public MethodMathsInRange(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtNumber.name, Zetta.dtNumber.name,
		Zetta.dtNumber.name } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns true if argument1 >= argument2 and argument1 <= argument3" };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 3)) {
	    if (Token.tokenCanBeNumber(args[0])) {
		if (Token.tokenCanBeNumber(args[1])) {
		    if (Token.tokenCanBeNumber(args[2])) return true;
		    else {
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
		Token.getDouble(args[1]), Token.getDouble(args[2]) };
	return asVariable(Zetta.dtNumber, ((a[0] >= a[1]) && (a[0] <= a[2])));
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtBoolean;
    }

}
