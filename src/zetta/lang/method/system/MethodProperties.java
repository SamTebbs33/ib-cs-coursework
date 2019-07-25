package zetta.lang.method.system;

import java.util.Properties;
import java.util.Set;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeDictionary;

/**
 * 
 * @author samueltebbs, 17:08:04 - 9 Sep 2014
 */
public class MethodProperties extends MethodProperty {

    public MethodProperties(final String name) {
	super(name);
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	return checkLength(args, true, 0);
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Properties properties = System.getProperties();
	final Variable v = new Variable(Zetta.dtDictionary, "",
		new Object[(properties.size() * 2) + 1]);
	properties.size();
	v.setIndex(0, Zetta.dtString);
	int c = 0;
	final Set<Object> keys = properties.keySet();
	for (final Object str : keys) {
	    DataTypeDictionary.setKey(v, c, str.toString());
	    DataTypeDictionary.setValue(v, c, new Variable(Zetta.dtString, "",
		    properties.get(str)));
	    c++;
	}
	return v;
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtDictionary;
    }

}
