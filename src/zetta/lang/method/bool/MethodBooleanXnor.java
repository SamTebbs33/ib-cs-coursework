package zetta.lang.method.bool;

/**
 * 
 * @author samueltebbs, 17:07:27 - 4 Sep 2014
 */
public class MethodBooleanXnor extends MethodBoolean {

    public MethodBooleanXnor(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns true if argument 1 and argument 2 are the same" };
    }

    @Override
    protected boolean bool(final boolean b1, final boolean b2) {
	return !(b1 ^ b2);
    }

}
