package zetta.lang.method.dictionary;

import zetta.Zetta;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.MappedDataType;

/**
 * 
 * @author samueltebbs, 20:43:15 - 1 Sep 2014
 */
public class MethodDictionaryValues extends MethodDictionaryKeys {

    public MethodDictionaryValues(final String name) {
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
	return new String[] { "Returns an Array containing the Dictionary's values" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtDictionary);
	return new Variable(Zetta.dtArray, "",
		((MappedDataType) v.dType).getAllMappedValues(v));
    }

}
