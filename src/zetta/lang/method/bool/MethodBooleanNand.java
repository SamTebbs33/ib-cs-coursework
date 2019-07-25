package zetta.lang.method.bool;

/**
 * 
 * @author samueltebbs, 17:06:06 - 4 Sep 2014
 */
public class MethodBooleanNand extends MethodBoolean {

    public MethodBooleanNand(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns false if argument 1 and argument 2 are true" };
    }

    @Override
    protected boolean bool(final boolean b1, final boolean b2) {
	return !(b1 && b2);
    }

}
