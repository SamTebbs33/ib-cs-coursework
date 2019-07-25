package zetta.lang.construct;

import zetta.Zetta;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenMethodCall;
import zetta.token.TokenVariable;

/**
 * 
 * @author samueltebbs, 14:35:58 - 22 Aug 2014
 */
public class ConstructForAll extends ConstructForEach {

    public ConstructForAll(final String name) {
	super(name);
    }

    @Override
    protected Variable doRepeatedEvaluation(final Variable v, final Token token) {
	if (token instanceof TokenVariable) return Zetta
		.getVariableOfType(token);
	else return Zetta.executeMethod((TokenMethodCall) token);
    }

}
