package zetta.lang.method.standard;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenVariable;

/**
 * 
 * @author samueltebbs, 18:57:38 - 5 Sep 2014
 */
public class MethodStandardString extends MethodSize {

    public MethodStandardString(final String name) {
	super(name);
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	return checkLength(args, true, 0);
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariable((TokenVariable) object);
	return asVariable(Zetta.dtString, v.dType.string(v));
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtString;
    }

}
