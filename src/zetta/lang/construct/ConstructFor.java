package zetta.lang.construct;

import zetta.Zetta;
import zetta.exception.UnexpectedArgumentQuantityException;
import zetta.exception.UnexpectedDataTypeException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Construct;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenConstruct;
import zetta.token.TokenMethodArgument;
import zetta.token.TokenVariable;

/**
 * A for construct Format: for(counter, initial value, max){ statements... }
 * 
 * @author samueltebbs
 * 
 */
public class ConstructFor extends Construct {

    public ConstructFor(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public boolean canConstruct(final Token[] tokens, final TokenConstruct t) {
	if (Method.checkLength(tokens, false, 3)
		|| Method.checkLength(tokens, false, 4)) {
	    if ((tokens[0] instanceof TokenVariable)
		    || (tokens[0] instanceof TokenMethodArgument)) {
		if (Token.tokenCanBeNumber(tokens[1])) {
		    if (Token.tokenCanBeNumber(tokens[2])) {
			if (tokens.length == 4) {
			    if (Token.tokenCanBeNumber(tokens[3])) return true;
			    else {
				new UnexpectedTokenException(tokens[2],
					Zetta.tokenExpression,
					Zetta.tokenMethodCall,
					Zetta.tokenNumber, Zetta.tokenVariable);
			    }
			}
			return true;
		    } else {
			new UnexpectedTokenException(tokens[2],
				Zetta.tokenExpression, Zetta.tokenMethodCall,
				Zetta.tokenNumber, Zetta.tokenVariable);
		    }
		} else {
		    new UnexpectedTokenException(tokens[1],
			    Zetta.tokenExpression, Zetta.tokenMethodCall,
			    Zetta.tokenNumber, Zetta.tokenVariable);
		}
	    } else {
		new UnexpectedTokenException(tokens[0], Zetta.tokenVariable,
			Zetta.tokenMethodArgument);
	    }
	} else {
	    new UnexpectedArgumentQuantityException(tokens.length, 4);
	}
	return true;
    }

    @Override
    public Variable run(final TokenConstruct token) {
	Variable counter = null;
	if (token.subTokens[0] instanceof TokenMethodArgument) {
	    if ((((TokenMethodArgument) token.subTokens[0]).dType == Zetta.dtInteger)
		    || (((TokenMethodArgument) token.subTokens[0]).dType == Zetta.dtNumber)) {
		counter = Zetta.dtInteger.initVariable(
			((TokenMethodArgument) token.subTokens[0]).value,
			new Token[] { token.subTokens[1] });
		token.localVariables.add(counter);
	    } else {
		new UnexpectedDataTypeException(counter, Zetta.dtString);
	    }
	} else {

	    counter = Zetta.getVariableOfType(token.subTokens[0],
		    Zetta.dtInteger);
	}
	int max = 0;
	counter.dType.setVariableToObject(counter,
		Token.getInteger(token.subTokens[1]));
	max = Token.getInteger(token.subTokens[2]);
	Zetta.lineNo++;
	int increment = 1;
	while (((int) counter.getIndex(0)) < max) {
	    if (token.subTokens.length == 4) {
		increment = Token.getInteger(token.subTokens[3]);
	    }
	    Zetta.runFromConstruct(token.body, token, true);
	    token.localVariables.add(counter);
	    max = Token.getInteger(token.subTokens[2]);
	    counter.dType.setVariableToObject(counter,
		    ((int) counter.getIndex(0)) + increment);
	}
	return null;
    }

}
