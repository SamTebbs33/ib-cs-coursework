package zetta.lang.method.bool;

/**
 * 
 * @author samueltebbs, 16:58:13 - 4 Sep 2014
 */
public class MethodBooleanOr extends MethodBoolean {

    public MethodBooleanOr(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns true if both or either arguments are true" };
    }

    @Override
    protected boolean bool(final boolean b1, final boolean b2) {
	return b1 || b2;
    }

}
