package zetta.lang.datatype;

import java.util.Random;

import zetta.Zetta;
import zetta.exception.UnexpectedDataTypeException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenMethodCall;
import zetta.token.TokenVariable;

/**
 * 
 * @author samueltebbs, 10:15:28 - 13 Sep 2014
 */
public class DataTypeRandom extends DataType {

    public DataTypeRandom(final String name) {
	super(name);
    }

    @Override
    public String[] constructDesc() {
	return new String[] {
		"Sets the random number generator's seed to the current system time",
		"Sets the random number generator's seed to the argument" };
    }

    @Override
    public String[][] constructArgs() {
	return new String[][] { { "" }, { Zetta.dtInteger.name } };
    }

    @Override
    public boolean canConstruct(final Token[] args, final String raw) {
	if (Method.checkLength(args, false, 0)) return true;
	else if (Method.checkLength(args, true, 1)) {
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
    public Variable initVariable(final String string, final Token[] args) {
	Random rand;
	long seed = 0;
	if (args.length == 0) {
	    seed = System.currentTimeMillis();
	    rand = new Random();
	} else {
	    seed = Token.getInteger(args[0]);
	    rand = new Random(seed);
	}
	final Variable v = new Variable(this, string,
		new Object[] { rand, seed });
	return v;
    }

    @Override
    public String stringDesc() {
	return "Returns the random number generator's seed";
    }

    @Override
    public String operatorAssignDesc() {
	return "If the operand is a method call, sets the variable to the result of the method call if it is of the same data type.\n"
		+ "If the operand is a variable, sets the variable to the operand if it is of the same data type.";
    }

    @Override
    public Variable operatorAssign(final Variable lhs, final Token token) {
	if (token instanceof TokenMethodCall) {
	    final Object v = Zetta.executeMethod((TokenMethodCall) token);
	    if (v instanceof Variable) {
		if (((Variable) v).dType == this) return (Variable) v;
		else {
		    new UnexpectedDataTypeException(((Variable) v).dType, this);
		}
	    }
	} else if (token instanceof TokenVariable) return Zetta
		.getVariableOfType(token, this);
	else {
	    new UnexpectedTokenException(token, Zetta.tokenMethodCall,
		    Zetta.tokenVariable);
	}
	return null;
    }

    @Override
    public String string(final Variable v) {
	// TODO Auto-generated method stub
	return v.getIndex(1).toString();
    }

}
