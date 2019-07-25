package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * 
 * @author samueltebbs, 14:47:30 - 7 Sep 2014
 */
public class MethodMathsFibonacci extends MethodMathsAbs {

    public MethodMathsFibonacci(final String name) {
	super(name);
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 1)) {
	    if (Token.tokenCanBeInteger(args[0])) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenInteger,
			Zetta.tokenMethodCall, Zetta.tokenVariable,
			Zetta.tokenExpression);
	    }
	}
	return false;
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns an Int Array with the argument as its length containing the fibonacci numbers" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final int quantity = Token.getInteger(args[0]);
	int a = 0, b = 1;
	final Variable v = new Variable(Zetta.dtArray, "",
		new Object[quantity + 3]);
	v.setIndex(0, Zetta.dtInteger);
	v.setIndex(1, new Variable(Zetta.dtInteger, "", a));
	v.setIndex(2, new Variable(Zetta.dtInteger, "", b));
	for (int c = 0; c < quantity; c++) {
	    final int t = a + b;
	    a = b;
	    b = t;
	    v.setIndex(c + 3, new Variable(Zetta.dtInteger, "", b));
	}
	return v;
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtArray;
    }

}
