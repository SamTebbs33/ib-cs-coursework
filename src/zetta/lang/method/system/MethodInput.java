package zetta.lang.method.system;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.method.MethodAbstract;
import zetta.token.TokenLiteral;
import zetta.token.TokenMethodCall;
import zetta.token.TokenString;
import zetta.token.TokenVariable;

public class MethodInput extends MethodAbstract {

    public MethodInput(final String name) {
	super(name);
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	// Ensure that the user has supplied the correct arguments
	if (checkLength(args, false, 0)) return true;
	else if (checkLength(args, true, 1)) {
	    if ((args[0] instanceof TokenLiteral)
		    || (args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenMethodCall)) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenString,
			Zetta.tokenNumber, Zetta.tokenVariable,
			Zetta.tokenMethodCall);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	if (args.length == 1) {
	    if (args[0] instanceof TokenLiteral) {
		Zetta.print(args[0].value);
	    } else if (args[0] instanceof TokenVariable) {
		final Variable v = Zetta.getVariableOfType(args[0]);
		Zetta.print(v.dType.string(v));
	    } else {
		final Object obj = ((TokenMethodCall) args[0]).method.execute(
			args[0].subTokens, ((TokenMethodCall) args[0]).object);
		if (obj != null) {
		    Zetta.print(obj.toString());
		} else {
		    Zetta.print();
		}
	    }
	}
	return Zetta.dtString.initVariable("", new Token[] { new TokenString(
		"d" + Zetta.in.nextLine() + "d") });
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtString;
    }

}
