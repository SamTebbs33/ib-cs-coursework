package zetta.lang.construct;

import zetta.Zetta;
import zetta.exception.UnexpectedDataTypeException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Construct;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenBooleanExpression;
import zetta.token.TokenConstruct;
import zetta.token.TokenMethodCall;
import zetta.token.TokenVariable;

/**
 * The if statement Format: if(Conditions...){ statements... }
 * 
 * @author samueltebbs
 */
public class ConstructIf extends Construct {

    public ConstructIf(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtBoolean.name } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Runs the block of code if the argument is true" };
    }

    @Override
    public String[][] docThrows() {
	return new String[][] {};
    }

    @Override
    public String[] docCodeExamples() {
	return new String[] {};
    }

    @Override
    public boolean canConstruct(final Token[] tokens, final TokenConstruct t) {
	if (Method.checkLength(tokens, true, 1)) {
	    if ((tokens[0] instanceof TokenBooleanExpression)
		    || (tokens[0] instanceof TokenVariable)
		    || (tokens[0] instanceof TokenMethodCall)) return true;
	    else {
		new UnexpectedTokenException(tokens[0],
			Zetta.tokenBooleanExpression, Zetta.tokenVariable,
			Zetta.tokenMethodCall);
	    }
	}
	return true;
    }

    @Override
    public Variable run(final TokenConstruct token) {
	boolean succeeded = false;
	if (token.subTokens[0] instanceof TokenBooleanExpression) {
	    if ((boolean) token.subTokens[0].evaluate() == true) {
		succeeded = true;
	    }
	} else if (token.subTokens[0] instanceof TokenVariable) {
	    final Variable v = Zetta.getVariableOfType(token.subTokens[0],
		    Zetta.dtBoolean);
	    if (Boolean.parseBoolean(v.dType.get(v).toString())) {
		succeeded = true;
	    }
	} else if (token.subTokens[0] instanceof TokenMethodCall) {
	    if (Zetta.getMethodReturnType((TokenMethodCall) token.subTokens[0]) == Zetta.dtBoolean) {
		final Variable obj = Zetta
			.executeMethod((TokenMethodCall) token.subTokens[0]);
		if ((boolean) obj.getIndex(0)) {
		    succeeded = true;
		}
	    } else {
		new UnexpectedDataTypeException(
			Zetta.getMethodReturnType((TokenMethodCall) token.subTokens[0]),
			Zetta.dtBoolean);
	    }
	}
	Zetta.startConstruct(token);
	if (succeeded) {
	    Zetta.runFromConstruct(token.body, token, false);
	}
	if ((Zetta.address + 1) < Zetta.programTokens.size()) {
	    final Token t = Zetta.programTokens.get(Zetta.address + 1);
	    if (t instanceof TokenConstruct) {
		if (((TokenConstruct) t).construct instanceof ConstructElse) {
		    if (succeeded) {
			Zetta.address++;
		    } else {
			Zetta.exitConstruct();
			((TokenConstruct) t).construct.run((TokenConstruct) t);
			Zetta.address++;
			return null;
		    }
		}
	    }
	}
	Zetta.exitConstruct();
	return null;
    }

}
