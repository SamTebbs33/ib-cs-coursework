package zetta.lang.method.standard;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenVariable;

public class MethodSize extends MethodStandard {

    public MethodSize(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	return checkLength(args, true, 0);
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariable((TokenVariable) object);
	return asVariable(Zetta.dtInteger, (double) v.dType.size(v));
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtInteger;
    }

}
