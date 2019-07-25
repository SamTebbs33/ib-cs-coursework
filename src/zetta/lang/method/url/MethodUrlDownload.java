package zetta.lang.method.url;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import zetta.Zetta;
import zetta.exception.EnumURLException;
import zetta.exception.FileException;
import zetta.exception.URLException;
import zetta.exception.UnexpectedDataTypeException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;
import zetta.token.TokenMethodCall;
import zetta.token.TokenString;
import zetta.token.TokenVariable;

/**
 * 
 * @author samueltebbs, 23:30:53 - 11 Sep 2014
 */
public class MethodUrlDownload extends MethodUrl {

    public MethodUrlDownload(final String name) {
	super(name);
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 1)) {
	    if ((args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenMethodCall)
		    || (args[0] instanceof TokenString)) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenVariable,
			Zetta.tokenMethodCall, Zetta.tokenString);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	File f = null;
	if (args[0] instanceof TokenVariable) {
	    f = DataTypeFile.getFile(Zetta.getVariableOfType(object,
		    Zetta.dtString));
	} else if (args[0] instanceof TokenMethodCall) {
	    if (Zetta.getMethodReturnType((TokenMethodCall) args[0]) == Zetta.dtString) {
		f = new File((String) Zetta.executeMethod(
			(TokenMethodCall) args[0]).getIndex(0));
	    } else {
		new UnexpectedDataTypeException(
			Zetta.getMethodReturnType((TokenMethodCall) args[0]),
			Zetta.dtString);
	    }
	} else if (args[0] instanceof TokenString) {
	    f = new File(args[0].value);
	}
	final URL url = (URL) Zetta.getVariableOfType(object, Zetta.dtURL)
		.getIndex(0);
	try {
	    url.openConnection().connect();
	} catch (final IOException e1) {
	    new URLException(url.toString(), EnumURLException.NO_CONNECTION, e1);
	}
	ReadableByteChannel rbc;
	try {
	    rbc = Channels.newChannel(url.openStream());
	    final FileOutputStream fos = new FileOutputStream(f);
	    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	    fos.close();
	    return asVariable(Zetta.dtBoolean, true);
	} catch (final IOException e) {
	    new FileException(f.getName(), e);
	}
	return asVariable(Zetta.dtBoolean, false);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtString.name } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Download the file at the url and save it as a new file using the String argument" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtBoolean.name };
    }

    @Override
    public String[][] docThrows() {
	return new String[][] { { FileException.class.getSimpleName(),
		URLException.class.getSimpleName() } };
    }

    @Override
    public String[] docCodeExamples() {
	return new String[] {};
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtBoolean;
    }

}
