package zetta.lang.method.system;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * 
 * @author samueltebbs, 14:27:10 - 22 Aug 2014
 */
public class MethodImport extends MethodProperty {

    public MethodImport(final String name) {
	super(name);
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final String s = Token.getString(args[0]);
	return asVariable(Zetta.dtBoolean, Zetta.loadLib(s));
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtBoolean;
    }

}
