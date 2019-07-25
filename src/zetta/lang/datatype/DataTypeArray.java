package zetta.lang.datatype;

import java.util.Arrays;

import zetta.Zetta;
import zetta.exception.InvalidIndexException;
import zetta.exception.UnexpectedDataTypeException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenDataType;
import zetta.token.TokenMethodCall;
import zetta.token.TokenNumber;
import zetta.token.TokenSequence;
import zetta.token.TokenVariable;

/**
 * Holds multiple values represented by an expandable Object[] Object[0] holds
 * the data type that all elements of the array must conform to Object[1...]
 * hold the value of the array
 * 
 * @author
 * @version 1.00 2014/7/3
 */

public class DataTypeArray extends DataType implements IterableDataType,
	CollectionDataType {

    public DataTypeArray(final String name) {
	super(name);
    }

    @Override
    public String sizeDesc() {
	return "Returns the number of elements in the array";
    }

    @Override
    public String stringDesc() {
	return "Returns the a String containing the elements of the Array separated by commas";
    }

    @Override
    public String docDescription() {
	return "Holds multiple values indexed by numbers";
    }

    @Override
    public String[][] constructArgs() {
	return new String[][] { { Method.TYPE_GENERIC } };
    }

    @Override
    public String[] constructDesc() {
	return new String[] { "Creates an Array that can store variables of type T" };
    }

    @Override
    public boolean canConstruct(final Token[] args, final String raw) {
	if (Method.checkLength(args, true, 1)) {
	    if (args[0] instanceof TokenDataType) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenDataType);
	    }
	}
	return false;
    }

    @Override
    public Variable initVariable(final String name, final Token[] args) {

	final DataType dType = ((TokenDataType) args[0]).dType;
	final Variable v = new Variable(this, name, new Object[1]);
	v.setIndex(0, dType);
	/*
	 * for (int c = 1; c < v.getValue().length; c++) { v.setIndex(c,
	 * dType.basicVar()); }
	 */
	return v;
    }

    @Override
    public long size(final Variable v) {
	return iteratorLength(v);
    }

    @Override
    public String string(final Variable v) {
	// TODO Auto-generated method stub
	return Arrays.toString(Arrays.copyOfRange(v.getValue(), 1,
		v.getValue().length));
    }

    @Override
    public Variable iteratorValue(final Variable v, final int index) {
	// TODO Auto-generated method stub
	if ((index < (v.getValue().length + 1)) && (index >= 0)) return (Variable) v
		.getIndex(index + 1);
	return null;
    }

    @Override
    public int iteratorLength(final Variable v) {
	// TODO Auto-generated method stub
	return v.getValue().length - 1;
    }

    @Override
    public int collectionLength(final Variable v) {
	// TODO Auto-generated method stub
	return iteratorLength(v);
    }

    @Override
    public void setCollectionIndex(final Variable v, final Token indexArg,
	    final Variable value) {
	final int index = Token.getInteger(indexArg);
	if ((index > -1) && (index < iteratorLength(v))) {
	    v.value[index + 1] = value;
	} else {
	    new InvalidIndexException(index, 0, iteratorLength(v));
	}
    }

    @Override
    public Variable getCollectionIndex(final Variable v, final Token indexArg) {
	final int index = Token.getInteger(indexArg);
	if ((index > -1) && (index < iteratorLength(v))) return (Variable) v.value[index + 1];
	else {
	    new InvalidIndexException(index, 0, iteratorLength(v) - 1);
	}
	return null;
    }

    @Override
    public Object iteratorKey(final Variable v, final int index) {
	// TODO Auto-generated method stub
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
	return (DataType) v.getIndex(0);
    }

    @Override
    public Variable operatorAssign(final Variable lhs, final Token token) {
	if (token instanceof TokenMethodCall) {
	    if (Zetta.getMethodReturnType((TokenMethodCall) token) == this) {
		final Variable v = Zetta.executeMethod((TokenMethodCall) token);
		if (v.dType == Zetta.dtArray) return v;
		else {
		    new UnexpectedDataTypeException(v.dType, Zetta.dtArray);
		}
	    } else {
		new UnexpectedDataTypeException(
			Zetta.getMethodReturnType((TokenMethodCall) token),
			Zetta.dtArray);
	    }
	} else if (token instanceof TokenVariable) {
	    final Variable v2 = Zetta.getVariableOfType(token, Zetta.dtArray);
	    final Variable v = new Variable(this, "", new Object[0]);
	    v.setValue(Arrays.copyOf(v2.getValue(), v2.getValue().length));
	    return v;
	} else if (token instanceof TokenSequence) {
	    DataType dType = Zetta.dtInteger;
	    for (final Token t : token.subTokens) {
		if (!(t instanceof TokenNumber)) {
		    if (t instanceof TokenNumber) {
			dType = Zetta.dtNumber;
		    } else {
			dType = Zetta.dtString;
			break;
		    }
		}
	    }
	    lhs.setValue(new Object[token.subTokens.length + 1]);
	    lhs.setIndex(0, dType);
	    for (int c = 0; c < token.subTokens.length; c++) {
		final Variable v2 = dType.initVariable("",
			new Token[] { token.subTokens[c] });
		if (v2 != null) {
		    lhs.setIndex(c + 1, v2);
		}
	    }
	    return lhs;
	} else {
	    new UnexpectedTokenException(token, Zetta.tokenMethodCall,
		    Zetta.tokenVariable, Zetta.tokenSequence);
	}
	return null;
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
	final Variable[] values = new Variable[v.getValue().length - 1];
	for (int c = 0; c < values.length; c++) {
	    values[c] = (Variable) v.getIndex(c + 1);
	}
	return values;
    }

    @Override
    public DataType iteratorKeyDataType(final Variable collection) {
	return Zetta.dtInteger;
    }

    @Override
    public Variable sortArray(final Variable obj) {
	return ((DataType) obj.getIndex(0)).sortArray(obj);
    }
}