package zetta.lang.method.array;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * 
 * @author samueltebbs, 18:15:47 - 3 Aug 2014
 */
public class MethodArraySort extends MethodArray {

    public MethodArraySort(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { {} };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns a sorted version of the Array. The sorting details depends on the data type accepted by this Array." };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtArray.name };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	return checkLength(args, true, 0);
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable obj = Zetta.getVariableOfType(object, Zetta.dtArray);
	return ((DataType) obj.getIndex(0)).sortArray(obj);
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtArray;
    }

}
