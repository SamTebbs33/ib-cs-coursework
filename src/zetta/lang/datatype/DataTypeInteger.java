package zetta.lang.datatype;

import java.util.Arrays;

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
 * @author samueltebbs, 20:40:50 - 14 Sep 2014
 */
public class DataTypeInteger extends DataTypeNumber {

    public DataTypeInteger(final String name) {
	super(name);
    }

    @Override
    public String docDescription() {
	return "Represents a whole number";
    }

    @Override
    public String sizeDesc() {
	return "Returns the int held by the variable";
    }

    @Override
    public String stringDesc() {
	return "Returns the number in string form";
    }

    @Override
    public String[][] constructArgs() {
	return new String[][] { { Zetta.dtInteger.name } };
    }

    @Override
    public String[] constructDesc() {
	return new String[] { "Sets the variable's value to the " + name };
    }

    @Override
    public boolean opEqual(final Variable v, final Object obj) {
	if (obj instanceof Variable) {
	    if (v.dType == ((Variable) obj).dType) return Arrays.equals(
		    v.getValue(), ((Variable) obj).getValue());
	} else if (obj instanceof Integer) return (Integer) obj == Integer
		.parseInt(v.getIndex(0).toString());
	else if (obj instanceof String) return ((String) obj).equals(string(v));
	return false;
    }

    @Override
    public boolean canConstruct(final Token[] args, final String raw) {
	if (Method.checkLength(args, true, 1)) {
	    if (Token.tokenCanBeInteger(args[0])) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenInteger,
			Zetta.tokenVariable, Zetta.tokenMethodCall,
			Zetta.tokenExpression);
	    }
	}
	return false;
    }

    @Override
    public Variable initVariable(final String name, final Token[] args) {
	final Variable v = new Variable(this, name, args[0].value);
	v.setIndex(0, Token.getInteger(args[0]));
	return v;
    }

    @Override
    public long size(final Variable v) {
	return (int) (Math.log10((double) v.getIndex(0)) + 1);
    }

    @Override
    public String string(final Variable v) {
	// TODO Auto-generated method stub
	return v.getIndex(0).toString();
    }

    @Override
    public Variable operatorAssign(final Variable lhs, final Token token) {
	if (token instanceof TokenMethodCall) {
	    final DataType d = Zetta
		    .getMethodReturnType((TokenMethodCall) token);
	    if ((d == this) || (d == Zetta.dtNumber)) return Zetta
		    .executeMethod((TokenMethodCall) token);
	    else {
		new UnexpectedDataTypeException(
			Zetta.getMethodReturnType((TokenMethodCall) token),
			this);
	    }
	} else if (token instanceof TokenVariable) return Zetta
		.getVariableOfType(token, this, Zetta.dtNumber);
	else return Method.asVariable(this, Token.getDouble(token));
	return null;
    }

    @Override
    public Variable sortArray(final Variable obj) {
	final Variable result = new Variable(Zetta.dtArray, "",
		new Object[obj.getValue().length]);
	final int[] numbers = new int[result.getValue().length - 1];
	for (int c = 0; c < numbers.length; c++) {
	    numbers[c] = (int) ((Variable) obj.getIndex(c + 1)).getIndex(0);
	}
	result.setValue(Arrays.copyOf(obj.getValue(), obj.getValue().length));
	boolean done = false;
	while (!done) {
	    done = true;
	    for (int c = 1; c < numbers.length; c++) {
		final int dA = numbers[c], dB = numbers[c - 1];
		if (dA < dB) {
		    done = false;
		    numbers[c - 1] = dA;
		    numbers[c] = dB;
		    final Variable temp = (Variable) result.getIndex(c);
		    result.setIndex(c, result.getIndex(c + 1));
		    result.setIndex(c + 1, temp);

		}
	    }
	}
	return result;
    }

}
