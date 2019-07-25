package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.lang.Token;
import zetta.lang.Variable;

public class MethodMathsE extends MethodMaths {

    public MethodMathsE(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns euler's constant" };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 0)) return true;
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtNumber, Math.E);
    }

}
