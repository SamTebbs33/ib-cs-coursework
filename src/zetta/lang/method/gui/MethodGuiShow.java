package zetta.lang.method.gui;

import javax.swing.JOptionPane;

import zetta.Zetta;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeGui;

/**
 * 
 * @author samueltebbs, 23:54:55 - 30 Jul 2014
 */
public class MethodGuiShow extends MethodGui {

    public MethodGuiShow(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { "" } };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtNull.name };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Displays the dialogue box" };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	return checkLength(args, true, 0);
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable obj = Zetta.getVariableOfType(object, Zetta.dtGui);
	final String title = obj.getIndex(DataTypeGui.INDEX_NAME).toString();
	final String message = obj.getIndex(DataTypeGui.INDEX_MESSAGE)
		.toString();
	final String iconStr = obj.getIndex(DataTypeGui.INDEX_ICON).toString();
	int icon = -1;
	switch (iconStr) {
	    case "info":
		icon = JOptionPane.INFORMATION_MESSAGE;
		break;
	    case "warning":
		icon = JOptionPane.WARNING_MESSAGE;
		break;
	    case "error":
		icon = JOptionPane.ERROR_MESSAGE;
		break;
	    case "plain":
		icon = JOptionPane.PLAIN_MESSAGE;
		break;
	    case "question":
		icon = JOptionPane.QUESTION_MESSAGE;
		break;
	}
	JOptionPane.showMessageDialog(null, message, title, icon);
	return null;
    }

}
