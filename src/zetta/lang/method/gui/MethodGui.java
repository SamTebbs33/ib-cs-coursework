package zetta.lang.method.gui;

import zetta.Zetta;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.method.MethodSpecific;

/**
 * 
 * @author samueltebbs, 23:53:27 - 30 Jul 2014
 */
public class MethodGui extends MethodSpecific {

    public MethodGui(final String name) {
	super(name, Zetta.dtGui.name);
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return null;
    }

}
