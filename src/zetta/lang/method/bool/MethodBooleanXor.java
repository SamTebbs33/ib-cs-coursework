package zetta.lang.method.bool;

/**
 * 
 * @author samueltebbs, 17:01:01 - 4 Sep 2014
 */
public class MethodBooleanXor extends MethodBoolean {

    public MethodBooleanXor(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns true only if either argument 1 or argument 2 are true, not both" };
    }

    @Override
    protected boolean bool(final boolean b1, final boolean b2) {
	return b1 ^ b2;
    }

}
