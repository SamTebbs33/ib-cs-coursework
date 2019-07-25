package zetta.lang.method.maths;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenSequence;
import zetta.token.TokenVariable;

/**
 * Zetta
 * 
 * @author samueltebbs, 15:27:44
 */
public class MethodMathsMean extends MethodMaths {

    public MethodMathsMean(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtArray.name } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns the mean of the Numb Array" };
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 1)) {
	    if ((args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenSequence)) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenVariable,
			Zetta.tokenSequence);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	double[] toCheck = new double[1];
	if (args[0] instanceof TokenVariable) {
	    final Variable v = Zetta.getVariableOfType(args[0], Zetta.dtArray);
	    if (v.getIndex(0) == Zetta.dtNumber) {
		for (int c = 1; c < v.getValue().length; c++) {
		    toCheck[c] = (double) ((Variable) v.getIndex(c))
			    .getIndex(0);
		}
	    }
	} else if (args[0] instanceof TokenSequence) {
	    toCheck = new double[args[0].subTokens.length];
	    int c = 0;
	    for (final Token t : args[0].subTokens) {
		toCheck[c] = Token.getDouble(t);
		c++;
	    }
	}
	double total = 0;
	for (final double element : toCheck) {
	    total += element;
	}
	return asVariable(Zetta.dtNumber, total / toCheck.length);

    }

}
