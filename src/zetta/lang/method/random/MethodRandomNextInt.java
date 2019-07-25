package zetta.lang.method.random;

import java.util.Random;

import zetta.Zetta;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.lang.Variable;

/**
 * 
 * @author samueltebbs, 10:32:55 - 13 Sep 2014
 */
public class MethodRandomNextInt extends MethodRandom {

    public MethodRandomNextInt(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] {};
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Returns a random integer using a linear congurnetial generator according to the following equation"
		+ "<br><b>(seed >>> (16))</b>, where <b>1L</b>"
		+ "<br>It also updates the seed to (seed * 0x5DEECE66DL + 0xBL) & ((1L) << 48 ) - 1)</b>, where <b>1L</b> is a 64bit integer." };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtInteger.name };
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
    public boolean canConstruct(final Token[] args) {
	if (Method.checkLength(args, false, 0)) return true;
	else if (Method.checkLength(args, true, 1)) {
	    if (Token.tokenCanBeNumber(args[0])) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenExpression,
			Zetta.tokenMethodCall, Zetta.tokenNumber,
			Zetta.tokenVariable);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	int val = 0;
	final Random rand = (Random) Zetta.getVariableOfType(object,
		Zetta.dtRandom).getIndex(0);
	if (args.length == 0) {
	    val = rand.nextInt();
	} else {
	    val = rand.nextInt(Token.getInteger(args[0]));
	}
	return asVariable(Zetta.dtInteger, val);
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtInteger;
    }

}
