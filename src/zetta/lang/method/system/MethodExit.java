package zetta.lang.method.system;

import zetta.Zetta;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.method.MethodAbstract;

/**
 * Zetta
 * 
 * @author samueltebbs, 17:06:47
 */
public class MethodExit extends MethodAbstract {

    public MethodExit(final String name) {
	super(name);
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	return checkLength(args, true, 0);
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	Zetta.exit();
	return null;
    }

}
