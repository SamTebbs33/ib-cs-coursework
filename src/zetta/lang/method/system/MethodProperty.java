package zetta.lang.method.system;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.method.MethodAbstract;
import zetta.token.TokenString;
import zetta.token.TokenVariable;

public class MethodProperty extends MethodAbstract {

    public MethodProperty(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 1)) {
	    if ((args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenString)) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenVariable,
			Zetta.tokenString);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	String s = "";
	if (args[0] instanceof TokenVariable) {
	    final Variable v = Zetta.getVariableOfType(args[0]);
	    s = v.dType.string(v);
	    return asVariable(Zetta.dtString, s);
	} else if (args[0] instanceof TokenString) {
	    s = args[0].value;
	} else {
	    new UnexpectedTokenException(args[0], Zetta.tokenVariable,
		    Zetta.tokenString);
	    Zetta.exit();
	}
	if (s.equals("version")) return asVariable(Zetta.dtString,
		Zetta.VERSION_FULL);
	final String var = System.getProperty(s);
	return asVariable(Zetta.dtString, var == null ? "" : var);
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtString;
    }

}
