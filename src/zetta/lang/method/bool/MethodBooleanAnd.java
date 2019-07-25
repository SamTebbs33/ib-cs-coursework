package zetta.lang.method.bool;

/**
 * 
 * @author samueltebbs, 16:57:34 - 4 Sep 2014
 */
public class MethodBooleanAnd extends MethodBoolean {

    public MethodBooleanAnd(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns true if argument 1 and argument 2 are true" };
    }

    @Override
    protected boolean bool(final boolean b1, final boolean b2) {
	return b1 && b2;
    }

}
