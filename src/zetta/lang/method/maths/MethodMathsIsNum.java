package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.IterableDataType;
import zetta.token.TokenMethodCall;
import zetta.token.TokenString;
import zetta.token.TokenVariable;

/**
 * Zetta
 * 
 * @author samueltebbs, 17:00:07
 */
public class MethodMathsIsNum extends MethodMathsAbs {

    public MethodMathsIsNum(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns true if the argument can be parsed as a number" };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 1)) {
	    if ((args[0] instanceof TokenString)
		    || (args[0] instanceof TokenMethodCall)
		    || (args[0] instanceof TokenVariable)) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenString,
			Zetta.tokenMethodCall, Zetta.tokenVariable);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	if (args[0] instanceof TokenVariable) {
	    final Variable v = Zetta.getVariableOfType(args[0]);
	    if (v.dType instanceof IterableDataType) {
		for (int c = 0; c < ((IterableDataType) v.dType)
			.iteratorLength(v); c++) {
		    if (!Token.isNumber(((IterableDataType) v.dType)
			    .iteratorValue(v, c).toString())) return asVariable(
			    Zetta.dtBoolean, false);
		}
		return asVariable(Zetta.dtBoolean, true);
	    }
	    return asVariable(Zetta.dtBoolean,
		    Token.isNumber(v.dType.string(v)));
	} else if (args[0] instanceof TokenMethodCall) {
	    final Object obj = Zetta.executeMethod((TokenMethodCall) args[0]);
	    if (obj != null) return asVariable(Zetta.dtBoolean,
		    Token.isNumber(obj.toString()));
	    return asVariable(Zetta.dtBoolean, false);
	}
	return asVariable(Zetta.dtBoolean, Token.isNumber(args[0].value));
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtBoolean;
    }

}
