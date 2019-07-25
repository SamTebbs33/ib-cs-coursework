package zetta.lang.method.dictionary;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.MappedDataType;

/**
 * 
 * @author samueltebbs, 20:09:57 - 1 Sep 2014
 */
public class MethodDictionaryKeys extends MethodDictionary {

    public MethodDictionaryKeys(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { "" } };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtArray.name };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns a String Array containing the Dictionary's keys" };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	return checkLength(args, true, 0);
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtDictionary);
	return new Variable(Zetta.dtArray, "",
		((MappedDataType) v.dType).getAllMappedKeys(v));
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtArray;
    }

}
