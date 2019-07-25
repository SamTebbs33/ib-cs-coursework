package zetta.lang.method.array;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.MappedDataType;
import zetta.lang.method.MethodLibrary;
import zetta.token.TokenMethodCall;
import zetta.token.TokenSequence;
import zetta.token.TokenVariable;

/**
 * 
 * @author samueltebbs, 18:27:02 - 1 Sep 2014
 */
public class MethodArrayToDict extends MethodLibrary {

    public MethodArrayToDict(final String name) {
	super(name, Zetta.dtArray.name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtArray.name, Zetta.dtArray.name } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns a Dictionary using argument 1 as the keys and argument 2 as the values" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtDictionary.name + "<T>" };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 2)) {
	    if ((args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenMethodCall)
		    || (args[0] instanceof TokenSequence)) {
		if ((args[1] instanceof TokenVariable)
			|| (args[1] instanceof TokenMethodCall)
			|| (args[1] instanceof TokenSequence)) return true;
		else {
		    new UnexpectedTokenException(args[1], Zetta.tokenVariable,
			    Zetta.tokenMethodCall, Zetta.tokenSequence);
		}
	    } else {
		new UnexpectedTokenException(args[0], Zetta.tokenVariable,
			Zetta.tokenMethodCall, Zetta.tokenSequence);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable list1 = Token.getList(args[0]);
	final Variable list2 = Token.getList(args[1]);
	final int length = Math.min(list1.getValue().length - 1,
		list2.getValue().length - 1);
	final Variable v = new Variable(Zetta.dtDictionary, "", new Object[1]);
	v.setIndex(0, list2.getIndex(0));
	for (int c = 1; c <= length; c++) {
	    final String key = ((Variable) list1.getIndex(c)).dType
		    .string((Variable) list1.getIndex(c));
	    ((MappedDataType) v.dType).setMappedValue(v,
		    (Variable) list2.getIndex(c), key);
	}
	return v;
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtDictionary;
    }

}
