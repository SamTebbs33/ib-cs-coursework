package zetta.lang.method.dictionary;

import zetta.Zetta;
import zetta.exception.InvalidKeyException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.IterableDataType;
import zetta.lang.datatype.MappedDataType;
import zetta.token.TokenLiteral;
import zetta.token.TokenMethodCall;
import zetta.token.TokenVariable;

public class MethodDictionaryGet extends MethodDictionary {

    public MethodDictionaryGet(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtString.name } };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { TYPE_GENERIC };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the value bound to the key represented by the argument" };
    }

    @Override
    public String[][] docThrows() {
	return new String[][] { { InvalidKeyException.class.getSimpleName() } };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 1)) {
	    if ((args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenLiteral)
		    || (args[0] instanceof TokenMethodCall)) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenVariable,
			Zetta.tokenNumber, Zetta.tokenString,
			Zetta.tokenMethodCall);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtDictionary);
	String index = "";
	if (args[0] instanceof TokenVariable) {
	    final Variable v2 = Zetta.getVariableOfType(args[0]);
	    index = v2.dType.string(v2);
	} else if (args[0] instanceof TokenLiteral) {
	    index = args[0].value.toString();
	} else if (args[0] instanceof TokenMethodCall) {
	    final Variable v2 = Zetta.executeMethod((TokenMethodCall) args[0]);
	    index = v2.dType.string(v2);
	}
	if (((MappedDataType) v.dType).hasMappedKey(v, index)) return asVariable(
		(DataType) v.getIndex(0),
		((IterableDataType) v.dType).iteratorValue(v, index));
	else {
	    new InvalidKeyException(index.toString());
	}
	return null;
    }

    @Override
    public DataType getReturnType(final Variable pointer) {
	return (DataType) pointer.getIndex(0);
    }

}
