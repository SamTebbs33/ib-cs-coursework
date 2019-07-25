package zetta.lang.method.bool;

/**
 * 
 * @author samueltebbs, 17:07:02 - 4 Sep 2014
 */
public class MethodBooleanNor extends MethodBoolean {

    public MethodBooleanNor(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns true if argument 1 and argument 2 are false" };
    }

    @Override
    protected boolean bool(final boolean b1, final boolean b2) {
	return !(b1 || b2);
    }

}
