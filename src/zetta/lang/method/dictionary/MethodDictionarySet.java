package zetta.lang.method.dictionary;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.MappedDataType;
import zetta.token.TokenLiteral;
import zetta.token.TokenMethodCall;
import zetta.token.TokenVariable;

public class MethodDictionarySet extends MethodDictionaryGet {

    public MethodDictionarySet(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtString.name, TYPE_GENERIC } };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtNull.name };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Sets the value bound to the key to argument 2" };
    }

    @Override
    public String[][] docThrows() {
	return new String[][] { {} };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 2)) {
	    if ((args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenLiteral)
		    || (args[0] instanceof TokenMethodCall)) {
		if ((args[1] instanceof TokenVariable)
			|| (args[1] instanceof TokenLiteral)
			|| (args[1] instanceof TokenMethodCall)) return true;
		else {
		    new UnexpectedTokenException(args[1], Zetta.tokenVariable,
			    Zetta.tokenNumber, Zetta.tokenString,
			    Zetta.tokenMethodCall);
		}
	    } else {
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
	if (args[1] instanceof TokenVariable) {
	    final Variable v2 = Zetta.getVariableOfType(args[1],
		    (DataType) v.getIndex(0));
	    ((MappedDataType) v.dType).setMappedValue(v, v2, index);
	} else if (args[1] instanceof TokenLiteral) {
	    final Variable v2 = ((DataType) v.getIndex(0)).initVariable("",
		    args[1]);
	    ((MappedDataType) v.dType).setMappedValue(v, v2, index);

	} else if (args[1] instanceof TokenMethodCall) {
	    if (Zetta.getMethodReturnType((TokenMethodCall) args[1]) == v
		    .getIndex(0)) {
		final Variable v2 = Zetta
			.executeMethod((TokenMethodCall) args[1]);
		((MappedDataType) v.dType).setMappedValue(v, v2, index);
	    }
	}
	return null;
    }

    @Override
    public DataType getReturnType(final Variable pointer) {
	return Zetta.dtNull;
    }
}
