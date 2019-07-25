package zetta.lang.method.num;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;

public class MethodNumMax extends MethodNum {

    public MethodNumMax(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 0)) return true;
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtNumber, Double.MAX_VALUE);
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtNumber;
    }

}
