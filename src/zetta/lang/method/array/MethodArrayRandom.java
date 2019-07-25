package zetta.lang.method.array;

import java.util.Random;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * Zetta
 * 
 * @author samueltebbs, 10:35:27 - 27 Jul 2014
 */
public class MethodArrayRandom extends MethodArray {

    public MethodArrayRandom(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { "" } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns a random element of the array" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { TYPE_GENERIC };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	return checkLength(args, true, 0);
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtArray);
	return asVariable((DataType) v.getIndex(0),
		v.getIndex(new Random().nextInt(v.getValue().length)));
    }

    @Override
    public DataType getReturnType(final Variable pointer) {
	return (DataType) pointer.getIndex(0);
    }

}
