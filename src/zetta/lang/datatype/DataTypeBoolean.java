package zetta.lang.datatype;

import zetta.Zetta;
import zetta.exception.UnexpectedDataTypeException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenBooleanExpression;
import zetta.token.TokenMethodCall;
import zetta.token.TokenVariable;

/**
 * Represents a value of true/false
 * 
 * @author samueltebbs
 */
public class DataTypeBoolean extends DataType {

    public DataTypeBoolean() {
	// TODO Auto-generated constructor stub
    }

    @Override
    public String docDescription() {
	return "Represents a boolean value of true or false";
    }

    @Override
    public String sizeDesc() {
	return "Returns the length of string()";
    }

    @Override
    public String stringDesc() {
	return "Returns a String representation of the boolean value";
    }

    @Override
    public String[][] constructArgs() {
	return new String[][] { { Zetta.dtBoolean.name } };
    }

    @Override
    public String[] constructDesc() {
	return new String[] { "Creates a Bool using the Bool argument" };
    }

    public DataTypeBoolean(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public boolean canConstruct(final Token[] args, final String raw) {
	if (Method.checkLength(args, true, 1)) {
	    if (args[0] instanceof TokenBooleanExpression) return true;
	    else {
		new UnexpectedTokenException(args[0],
			Zetta.tokenBooleanExpression);
	    }
	}
	return false;
    }

    @Override
    public Variable initVariable(final String string, final Token[] args) {
	final Variable v = new Variable(Zetta.dtBoolean, string, new Object[1]);
	if (args[0].value.equals("true")) {
	    v.setIndex(0, true);
	} else if (args[0].value.equals("false")) {
	    v.setIndex(0, false);
	} else {}
	return v;
    }

    @Override
    public long size(final Variable v) {
	return v.getIndex(0).toString().length();
    }

    @Override
    public String string(final Variable v) {
	// TODO Auto-generated method stub
	return v.getIndex(0).toString();
    }

    @Override
    public Variable operatorAssign(final Variable lhs, final Token token) {
	if (token instanceof TokenMethodCall) {
	    final Object v = Zetta.executeMethod((TokenMethodCall) token);
	    if (v instanceof Variable) {
		if (((Variable) v).dType == Zetta.dtBoolean) return (Variable) v;
		else {
		    new UnexpectedDataTypeException(((Variable) v).dType,
			    Zetta.dtBoolean);
		}
	    }
	} else if (token instanceof TokenVariable) return Zetta
		.getVariableOfType(token, Zetta.dtBoolean);
	else if (token instanceof TokenBooleanExpression) return Zetta.dtBoolean
		.initVariable(lhs.name, token);
	else {
	    new UnexpectedTokenException(token, Zetta.tokenMethodCall,
		    Zetta.tokenVariable, Zetta.tokenBooleanExpression);
	}
	return null;
    }

    @Override
    public Variable sortArray(final Variable v) {
	final Variable result = new Variable(Zetta.dtArray, "",
		new Object[v.getValue().length]);
	int trueCount = 0, falseCount = 0;
	for (int c = 1; c < v.getValue().length; c++) {
	    if ((boolean) ((Variable) v.getIndex(c)).getIndex(0)) {
		trueCount++;
	    } else {
		falseCount++;
	    }
	}
	for (int c = 0; c < trueCount; c++) {
	    result.setIndex(c + 1, Method.asVariable(Zetta.dtBoolean, true));
	}
	for (int c = 0; c < falseCount; c++) {
	    result.setIndex(c + trueCount + 1,
		    Method.asVariable(Zetta.dtBoolean, false));
	}
	return result;
    }

}
