package zetta.lang.construct;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenBooleanExpression;
import zetta.token.TokenConstruct;
import zetta.token.TokenMethodCall;
import zetta.token.TokenVariable;

/**
 * An else construct Format: else(Conditions...){ statements... }
 * 
 * @author samueltebbs
 * 
 */
public class ConstructElse extends ConstructIf {

    public ConstructElse(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { {}, { Zetta.dtBoolean.name } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] {
		"If the previous block of code is an if construct and it evaluates to false, this block of code is run",
		"Like the previous else example but the argument must evaluate to true" };
    }

    @Override
    public boolean canConstruct(final Token[] tokens, final TokenConstruct t) {
	if (Method.checkLength(tokens, false, 1)) {
	    if ((tokens[0] instanceof TokenBooleanExpression)
		    || (tokens[0] instanceof TokenVariable)
		    || (tokens[0] instanceof TokenMethodCall)) return true;
	    else {
		new UnexpectedTokenException(tokens[0],
			Zetta.tokenBooleanExpression, Zetta.tokenVariable,
			Zetta.tokenMethodCall);
	    }
	} else if (Method.checkLength(tokens, true, 0)) {}
	return true;
    }

    @Override
    public Variable run(final TokenConstruct token) {
	if (token.subTokens.length == 1) {
	    boolean succeeded = false;
	    if (Boolean.parseBoolean(token.subTokens[0].evaluate().toString())) {
		Zetta.runFromConstruct(token.body, token, true);
		succeeded = true;
	    } else {
		if (((Zetta.address + 1) < Zetta.programTokens.size())
			&& !succeeded) {
		    final Token t = Zetta.programTokens.get(Zetta.address + 1);
		    if (t instanceof TokenConstruct) {
			if (((TokenConstruct) t).construct instanceof ConstructElse) {
			    ((TokenConstruct) t).construct
				    .run((TokenConstruct) t);
			}
		    }
		}
	    }
	} else {
	    Zetta.runFromConstruct(token.body, token, true);
	}
	return null;
    }
}
