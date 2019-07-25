package zetta.lang.method.num;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;

public class MethodNumMin extends MethodNumMax {

    public MethodNumMin(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtNumber, Double.MIN_VALUE);
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtNumber;
    }

}
