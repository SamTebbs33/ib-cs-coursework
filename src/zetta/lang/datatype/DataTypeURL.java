package zetta.lang.datatype;

import java.net.MalformedURLException;
import java.net.URL;

import zetta.Zetta;
import zetta.exception.URLException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.token.TokenPlaceholder;

/**
 * 
 * @author samueltebbs, 19:21:43 - 23 Aug 2014
 */
public class DataTypeURL extends DataType {

    public DataTypeURL(final String name) {
	super(name);
    }

    @Override
    public String docDescription() {
	return "Represents either a local or remote URL";
    }

    @Override
    public String sizeDesc() {
	return "Returns 0";
    }

    @Override
    public String stringDesc() {
	return "Returns the path used to initialise the variable";
    }

    @Override
    public String[][] constructArgs() {
	return new String[][] { { Zetta.dtString.name } };
    }

    @Override
    public String[] constructDesc() {
	return new String[] { "Sets the URL path to the String" };
    }

    @Override
    public boolean canConstruct(final Token[] args, final String raw) {
	if (Method.checkLength(args, true, 1)) {
	    if (args[0] instanceof TokenPlaceholder) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenMethodCall,
			Zetta.tokenNumber, Zetta.tokenString,
			Zetta.tokenVariable);
	    }
	}
	return false;
    }

    @Override
    public Variable initVariable(final String string, final Token[] args) {
	final Variable v = new Variable(this, string, new Object[1]);
	try {
	    v.setIndex(0, new URL(Token.getString(args[0])));
	} catch (final MalformedURLException ex) {
	    new URLException(Token.getString(args[0]), ex);
	}
	return v;
    }

    @Override
    public String toString() {
	return name;
    }

    @Override
    public long size(final Variable v) {
	return 0;
    }

    @Override
    public Variable sortArray(final Variable obj) {
	return obj;
    }

}
