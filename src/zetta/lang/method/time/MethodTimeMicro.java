package zetta.lang.method.time;

import zetta.Zetta;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * 
 * @author samueltebbs, 18:04:52 - 3 Aug 2014
 */
public class MethodTimeMicro extends MethodTimeMilli {

    public MethodTimeMicro(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the system time in microseconds" };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	return Method.checkLength(args, true, 0);
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return asVariable(Zetta.dtInteger, System.nanoTime() / 1000);
    }

}
