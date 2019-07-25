package zetta.lang.datatype;

import java.util.Arrays;

import zetta.Zetta;
import zetta.exception.UnexpectedDataTypeException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenLiteral;
import zetta.token.TokenMethodCall;
import zetta.token.TokenString;
import zetta.token.TokenVariable;

/**
 * Stores a string of text
 */
public class DataTypeString extends DataType implements IterableDataType {

    public DataTypeString(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String docDescription() {
	return "Represents a string of characters";
    }

    @Override
    public String sizeDesc() {
	return "Returns the length of the String";
    }

    @Override
    public String stringDesc() {
	return "Returns the String";
    }

    @Override
    public String[][] constructArgs() {
	return new String[][] { { Zetta.dtString.name } };
    }

    @Override
    public String[] constructDesc() {
	return new String[] { "Retrieves a string from argument 1 and stores it in the variable" };
    }

    @Override
    public boolean canConstruct(final Token[] args, final String raw) {
	if (Method.checkLength(args, true, 1)) {
	    if ((args[0] instanceof TokenString)
		    || (args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenMethodCall)) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenString,
			Zetta.tokenVariable);
	    }
	}
	return false;
    }

    @Override
    public Variable initVariable(final String name, final Token[] args) {
	Object value = null;
	if (args[0] instanceof TokenVariable) {
	    final Variable v2 = Zetta
		    .getVariableOfType(args[0], Zetta.dtString);
	    value = v2.dType.string(v2);
	} else if (args[0] instanceof TokenString) {
	    value = args[0].value;
	} else if (args[0] instanceof TokenMethodCall) {
	    value = ((TokenMethodCall) args[0]).method.execute(
		    args[0].subTokens, ((TokenMethodCall) args[0]).object);
	} else {
	    new UnexpectedTokenException(args[0], Zetta.tokenString,
		    Zetta.tokenVariable);
	    return null;
	}
	return new Variable(this, name, value);
    }

    @Override
    public long size(final Variable v) {
	return string(v).length();
    }

    @Override
    public String string(final Variable v) {
	return v.getIndex(0).toString();
    }

    @Override
    public boolean opEqual(final Variable v, final Object obj) {
	if (obj instanceof Variable) {
	    if (v.dType == ((Variable) obj).dType) return string(v).equals(
		    string((Variable) obj));
	} else if (obj instanceof Double) return (Double) obj == size(v);
	else if (obj instanceof String) return ((String) obj).equals(string(v));
	return false;
    }

    @Override
    public Variable iteratorValue(final Variable v, final int index) {
	if ((index < iteratorLength(v)) && (index >= 0)) return Method
		.asVariable(Zetta.dtString, string(v).charAt(index));
	return null;
    }

    @Override
    public int iteratorLength(final Variable v) {
	return string(v).length();
    }

    @Override
    public Object iteratorKey(final Variable v, final int index) {
	return index;
    }

    @Override
    public boolean iteratorHasIndex(final Variable v, final Object index) {
	if (Token.isNumber(index.toString())) {
	    final int i = (int) Double.parseDouble(index.toString());
	    return (i > -1) && (i < iteratorLength(v));
	}
	return false;
    }

    @Override
    public DataType iteratorDataType(final Variable v) {
	return Zetta.dtString;
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
	} else if (token instanceof TokenVariable) {
	    final Variable v = Zetta.getVariableOfType(token);
	    return new Variable(this, "", new Object[] { v.dType.string(v) });
	} else if (token instanceof TokenLiteral) return new Variable(this, "",
		new Object[] { token.value });
	else {
	    new UnexpectedTokenException(token, Zetta.tokenMethodCall,
		    Zetta.tokenVariable, Zetta.tokenNumber, Zetta.tokenString);
	}
	return null;
    }

    @Override
    public Variable basicVar() {
	return new Variable(this, "", new Token[] { new TokenString("dd") });
    }

    @Override
    public Variable iteratorValue(final Variable v, final String index) {
	new UnexpectedDataTypeException(Zetta.dtString, Zetta.dtInteger);
	return null;
    }

    @Override
    public Variable[] getAllIteratorIndexes(final Variable v) {
	final Variable[] indexes = new Variable[v.getValue().length - 1];
	for (int c = 0; c < indexes.length; c++) {
	    indexes[c] = Method.asVariable(Zetta.dtInteger, c);
	}
	return indexes;
    }

    @Override
    public Variable[] getAllIteratorValues(final Variable v) {
	final Variable[] values = new Variable[((String) v.getIndex(0))
		.length()];
	for (int c = 0; c < values.length; c++) {
	    values[c] = Method.asVariable(Zetta.dtString,
		    ((String) v.getIndex(0)).charAt(c));
	}
	return values;
    }

    @Override
    public DataType iteratorKeyDataType(final Variable collection) {
	return Zetta.dtInteger;
    }

    @Override
    public Variable sortArray(final Variable obj) {
	final Variable result = new Variable(Zetta.dtArray, "",
		new Object[obj.getValue().length]);
	result.setValue(Arrays.copyOf(obj.getValue(), obj.getValue().length));
	final String[] strings = new String[result.getValue().length - 1];
	for (int c = 0; c < strings.length; c++) {
	    strings[c] = (String) ((Variable) result.getIndex(c + 1))
		    .getIndex(0);
	}
	boolean done = false;
	while (!done) {
	    done = true;
	    for (int c = 1; c < strings.length; c++) {
		final String str1 = strings[c], str2 = strings[c - 1];
		if (str1.charAt(0) < str2.charAt(0)) {
		    done = false;
		    strings[c] = str2;
		    strings[c - 1] = str1;
		    final Variable temp = (Variable) result.getIndex(c);
		    result.setIndex(c, result.getIndex(c + 1));
		    result.setIndex(c + 1, temp);
		}
	    }
	}
	return result;
    }

}
