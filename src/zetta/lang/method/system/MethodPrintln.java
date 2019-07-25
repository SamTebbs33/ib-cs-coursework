package zetta.lang.method.system;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.method.MethodAbstract;
import zetta.token.TokenLiteral;
import zetta.token.TokenMethodCall;
import zetta.token.TokenVariable;

public class MethodPrintln extends MethodAbstract {

    protected static boolean hasPrinted = false;

    public MethodPrintln(final String name) {
	super(name);
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	for (final Token t : args) {
	    if ((t instanceof TokenVariable) || (t instanceof TokenLiteral)
		    || (t instanceof TokenMethodCall)) return true;
	    else {
		new UnexpectedTokenException(t, Zetta.tokenVariable,
			Zetta.tokenString, Zetta.tokenNumber,
			Zetta.tokenMethodCall);
	    }
	}
	return true;
    }

    protected String getPrintPrefix() {
	return !hasPrinted ? "" : "\n";
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	String val = "";
	if (args.length == 0) {
	    Zetta.print(getPrintPrefix() + "");
	    hasPrinted = true;
	} else {
	    for (final Token t : args) {
		val = Token.getString(t);
		Zetta.print(getPrintPrefix() + val);
		hasPrinted = true;
	    }
	}
	return null;
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtString;
    }

}
