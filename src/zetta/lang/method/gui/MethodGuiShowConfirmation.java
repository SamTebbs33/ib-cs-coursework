package zetta.lang.method.gui;

import javax.swing.JOptionPane;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeGui;

/**
 * Zetta
 * 
 * @author samueltebbs, 00:13:42 - 11 Oct 2014
 */
public class MethodGuiShowConfirmation extends MethodGuiShow {

    public MethodGuiShowConfirmation(final String name) {
	super(name);
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtInteger.name };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Displays the gui with the following buttons:"
		+ "<br>" + "<ul>" + "<li><b>Yes</b>: returns 0</li>"
		+ "<li><b>No</b>: returns 1</li>"
		+ "<li><b>Cancel</b>: returns 2</li>" };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	return checkLength(args, true, 0);
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtInteger;
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
	final int res = JOptionPane.showConfirmDialog(null, message, title,
		icon);
	return asVariable(Zetta.dtInteger, res);
    }

}
