package zetta.lang.method.gui;

import javax.swing.JOptionPane;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeGui;

/**
 * Zetta
 * 
 * @author samueltebbs, 17:29:49 - 10 Oct 2014
 */
public class MethodGuiShowInput extends MethodGuiShow {

    public MethodGuiShowInput(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { "" } };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtString.name };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Displays the dialogue box with an input box and returns the input" };
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtString;
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, false, 1)) {
	    if (Token.tokenCanBeString(args[0])) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenString,
			Zetta.tokenMethodCall, Zetta.tokenVariable);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable obj = Zetta.getVariableOfType(object, Zetta.dtGui);
	final String title = obj.getIndex(DataTypeGui.INDEX_NAME).toString();
	final String message = obj.getIndex(DataTypeGui.INDEX_MESSAGE)
		.toString();
	final String iconStr = obj.getIndex(DataTypeGui.INDEX_ICON).toString();
	String result = "";
	int icon = -1;
	final String defVal = Token.getString(args[0]);
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
	result = JOptionPane.showInputDialog(null, message, title, icon);
	if (result == null) {
	    result = defVal;
	}
	return asVariable(Zetta.dtString, result);
    }

}
