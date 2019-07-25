package zetta.lang.datatype;

import zetta.Zetta;
import zetta.exception.InvalidKeyException;
import zetta.exception.UnexpectedDataTypeException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenDataType;
import zetta.token.TokenMethodCall;
import zetta.token.TokenVariable;

/**
 * Holds multiple values mapped to keys in a LinkedHashMap used in
 * VariableMapped
 * 
 * @author samueltebbs
 */
public class DataTypeDictionary extends DataTypeArray implements
	IterableDataType, MappedDataType, CollectionDataType {

    public static final String DICT_START = "{", DICT_END = "}",
	    DICT_SEP = ":";

    public DataTypeDictionary(final String name) {
	super(name);
    }

    @Override
    public String sizeDesc() {
	return "Returns the number of keys in the Dictionary";
    }

    @Override
    public String stringDesc() {
	return "Returns the a String containing the keys and values in the Dictionary separated by commas and colons";
    }

    @Override
    public String docDescription() {
	return "Holds multiple values indexed by string keys";
    }

    @Override
    public String[][] constructArgs() {
	return new String[][] { { Method.TYPE_GENERIC } };
    }

    @Override
    public String[] constructDesc() {
	return new String[] { "Creates a Dictionary that can store variables of type T" };
    }

    @Override
    public Variable initVariable(final String name, final Token[] args) {
	final DataType dType = ((TokenDataType) args[0]).dType;
	final Variable v = new Variable(this, name, dType);
	/*
	 * for (int c = 1; c < v.getValue().length; c++) { v.setIndex(c,
	 * dType.basicVar()); }
	 */
	return v;
    }

    @Override
    public Variable getMappedValue(final Variable v, final String key) {
	for (int c = 0; c < (int) (size(v)); c++) {
	    if (getKey(v, c).equals(key)) return getValue(v, c);
	}
	new InvalidKeyException(key);
	return null;
    }

    @Override
    public boolean hasMappedKey(final Variable v, final String key) {
	for (int c = 0; c < (int) (size(v)); c++) {
	    if (getKey(v, c).equals(key)) return true;
	}
	return false;
    }

    @Override
    public boolean hasMappedValue(final Variable v, final Variable value) {
	for (int c = 0; c < size(v); c++) {
	    if (getValue(v, c).equals(v)) return true;
	}
	return false;
    }

    @Override
    public int getMappedSize(final Variable v) {
	return (int) size(v);
    }

    @Override
    public int iteratorLength(final Variable v) {
	// TODO Auto-generated method stub
	return (int) size(v);
    }

    @Override
    public void setMappedKey(final Variable v, final String key,
	    final Variable value) {
	for (int c = 0; c < size(v); c++) {
	    if (getKey(v, c).equals(value)) {
		setValue(v, c, new Variable(Zetta.dtString, "", key));
	    }
	}
    }

    @Override
    public void setMappedValue(final Variable v, final Variable value,
	    final String key) {
	for (int c = 0; c < size(v); c++) {
	    if (getKey(v, c).equals(key)) {
		setValue(v, c, value);
		return;
	    }
	}
	final Object[] array = new Object[v.getValue().length + 2]; // Add two
								    // since
								    // we're
								    // adding a
								    // key and
								    // value
	array[0] = v.getIndex(0);
	for (int c = 0; c < size(v); c++) {
	    array[c + 1] = getValue(v, c);
	}
	array[(int) (size(v) + 1)] = value;
	for (int c = (int) (size(v) + 2); c < (array.length - 1); c++) {
	    array[c] = v.getIndex(c - 1);
	}
	array[array.length - 1] = key;
	v.setValue(array);
    }

    @Override
    public Variable iteratorValue(final Variable v, final String index) {
	return getMappedValue(v, index);
    }

    @Override
    public Variable iteratorValue(final Variable v, final int index) {
	getAllMappedValues(v);
	if ((index < size(v)) && (index >= 0)) return getValue(v, index);
	return null;
    }

    @Override
    public Object iteratorKey(final Variable v, final int index) {
	return getKey(v, index);
    }

    @Override
    public boolean iteratorHasIndex(final Variable v, final Object index) {
	return hasMappedKey(v, index.toString());
    }

    @Override
    public long size(final Variable v) {
	return v.getValue().length / 2;
    }

    @Override
    public String string(final Variable v) {
	// TODO Auto-generated method stub
	String result = DICT_START;
	for (int c = 0; c < size(v); c++) {
	    final Variable val = getValue(v, c);
	    result += getKey(v, c) + DICT_SEP + val.dType.string(val);
	    if (c < (size(v) - 1)) {
		result += ", ";
	    }
	}
	return result += DICT_END;
    }

    @Override
    public Object get(final Variable v) {
	return v.getIndex(0);
    }

    @Override
    public void setVariableToVariable(final Variable from, final Variable to) {
	if (from.dType == to.dType) {
	    from.value = to.value;
	}
    }

    @Override
    public boolean opEqual(final Variable v, final Object obj) {
	if (obj instanceof Variable) {
	    if (v.dType == ((Variable) obj).dType) return v.getIndex(0).equals(
		    ((Variable) obj).getIndex(0));
	} else if (obj instanceof Double) return (Double) obj == size(v);
	else if (obj instanceof String) return ((String) obj).length() == size(v);
	return false;
    }

    @Override
    public DataType iteratorDataType(final Variable v) {
	return (DataType) v.getIndex(0);
    }

    @Override
    public Variable operatorAssign(final Variable lhs, final Token token) {
	if (token instanceof TokenMethodCall) {
	    final DataType d = Zetta
		    .getMethodReturnType((TokenMethodCall) token);
	    if (d == this) return Zetta.executeMethod((TokenMethodCall) token);
	    else {
		new UnexpectedDataTypeException(d, this);
	    }
	} else if (token instanceof TokenVariable) return Zetta
		.getVariableOfType(token, Zetta.dtDictionary);
	else {
	    new UnexpectedTokenException(token, Zetta.tokenMethodCall,
		    Zetta.tokenVariable);
	}
	return null;
    }

    @Override
    public int collectionLength(final Variable v) {
	// TODO Auto-generated method stub
	return iteratorLength(v);
    }

    @Override
    public void setCollectionIndex(final Variable v, final Token key,
	    final Variable value) {
	setMappedValue(v, value, Token.getString(key));
    }

    @Override
    public Variable getCollectionIndex(final Variable v, final Token key) {
	return iteratorValue(v, Token.getString(key));
    }

    @Override
    public Object[] getAllMappedKeys(final Variable v) {
	final Object[] keys = new Object[(int) size(v) + 1];
	keys[0] = Zetta.dtString;
	for (int c = 0; c < (keys.length - 1); c++) {
	    keys[c + 1] = new Variable(Zetta.dtString, "", getKey(v, c));
	}
	return keys;
    }

    @Override
    public Object[] getAllMappedValues(final Variable v) {
	final Object[] values = new Object[(int) size(v) + 1];
	values[0] = v.getIndex(0);
	for (int c = 1; c < values.length; c++) {
	    values[c] = v.getIndex(c);
	}
	return values;
    }

    public String getKey(final Variable v, final int index) {
	return (String) v.getIndex((int) (index + 1 + size(v)));
    }

    public Variable getValue(final Variable v, final int index) {
	return (Variable) v.getIndex(index + 1);
    }

    public static void setKey(final Variable v, final int index,
	    final String key) {
	v.setIndex((int) (index + 1 + v.dType.size(v)), key);
    }

    public static void setValue(final Variable v, final int index,
	    final Variable value) {
	v.setIndex(index + 1, value);
    }

    @Override
    public Variable[] getAllIteratorIndexes(final Variable v) {
	final Variable[] indexes = new Variable[(int) size(v)];
	for (int c = 0; c < indexes.length; c++) {
	    indexes[c] = Method.asVariable(Zetta.dtString, getKey(v, c));
	}
	return indexes;
    }

    @Override
    public Variable[] getAllIteratorValues(final Variable v) {
	final Variable[] values = new Variable[(int) size(v)];
	for (int c = 0; c < values.length; c++) {
	    values[c] = getValue(v, c);
	}
	return values;
    }

    @Override
    public DataType iteratorKeyDataType(final Variable collection) {
	return Zetta.dtString;
    }

    @Override
    public Variable sortArray(final Variable obj) {
	return obj;
    }

}