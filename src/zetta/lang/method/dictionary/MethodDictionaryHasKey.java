package zetta.lang.method.dictionary;

import zetta.Zetta;
import zetta.exception.UnexpectedDataTypeException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.IterableDataType;
import zetta.lang.datatype.MappedDataType;
import zetta.token.TokenLiteral;
import zetta.token.TokenMethodCall;
import zetta.token.TokenVariable;

public class MethodDictionaryHasKey extends MethodDictionaryGet {

    public MethodDictionaryHasKey(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtString.name } };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtBoolean.name };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns true if the Dictionary has the key represented by the argument" };
    }

    @Override
    public String[][] docThrows() {
	return new String[][] { {} };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtDictionary);
	String index = "";
	if (args[0] instanceof TokenVariable) {
	    final Variable v2 = Zetta.getVariableOfType(args[0]);
	    if (v.dType instanceof IterableDataType) {
		for (int c = 0; c < ((IterableDataType) v2.dType)
			.iteratorLength(v2); c++) {
		    final Object o = ((IterableDataType) v2.dType)
			    .iteratorValue(v2, c);
		    if (!((IterableDataType) v.dType).iteratorHasIndex(v, o)) return asVariable(
			    Zetta.dtBoolean, false);
		}
		return asVariable(Zetta.dtBoolean, true);
	    }
	    index = v2.dType.string(v2);
	} else if (args[0] instanceof TokenLiteral) {
	    index = args[0].value.toString();
	} else if (args[0] instanceof TokenMethodCall) {
	    if (Zetta.getMethodReturnType((TokenMethodCall) args[0]) == Zetta.dtString) {
		final Variable v2 = Zetta
			.executeMethod((TokenMethodCall) args[0]);
		index = v2.dType.string(v2);
	    } else {
		new UnexpectedDataTypeException(
			Zetta.getMethodReturnType((TokenMethodCall) args[0]),
			Zetta.dtString);
	    }
	}
	return asVariable(Zetta.dtBoolean,
		((MappedDataType) v.dType).hasMappedKey(v, index));
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtBoolean;
    }

}
