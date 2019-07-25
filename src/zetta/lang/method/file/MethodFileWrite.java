package zetta.lang.method.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import zetta.Zetta;
import zetta.exception.EnumFileException;
import zetta.exception.FileException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;
import zetta.lang.datatype.IterableDataType;
import zetta.token.TokenMethodCall;
import zetta.token.TokenNumber;
import zetta.token.TokenString;
import zetta.token.TokenVariable;

public class MethodFileWrite extends MethodFile {

    protected static boolean hasPrinted = false;

    public MethodFileWrite(final String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Writes the argument to the end of the file",
		"Writes each value to the end file" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtNull.name, Zetta.dtNull.name };
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtString.name }, { Zetta.dtArray.name } };
    }

    @Override
    public String[][] docThrows() {
	return new String[][] { { FileException.class.getSimpleName() },
		{ FileException.class.getSimpleName() } };
    }

    protected String getPrintPrefix() {
	return "";
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 1)) {
	    if ((args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenNumber)
		    || (args[0] instanceof TokenMethodCall)
		    || (args[0] instanceof TokenString)) return true;
	    else {
		new UnexpectedTokenException(args[0], Zetta.tokenVariable,
			Zetta.tokenMethodCall, Zetta.tokenString,
			Zetta.tokenNumber);
	    }
	}
	return false;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariableOfType(object, Zetta.dtFile);
	String[] toWrite = null;
	if (args[0] instanceof TokenVariable) {
	    final Variable v2 = Zetta.getVariableOfType(args[0]);
	    if (v2.dType instanceof IterableDataType) {
		toWrite = new String[((IterableDataType) v2.dType)
			.iteratorLength(v2)];
		for (int c = 0; c < toWrite.length; c++) {
		    toWrite[c] = ((IterableDataType) v2.dType).iteratorValue(
			    v2, c).toString();
		}
	    } else {
		toWrite = new String[] { v2.dType.string(v2) };
	    }
	} else if (args[0] instanceof TokenMethodCall) {
	    toWrite = new String[] { ((TokenMethodCall) args[0]).method
		    .execute(args[0].subTokens, null).toString() };
	} else if ((args[0] instanceof TokenString)
		|| (args[0] instanceof TokenNumber)) {
	    toWrite = new String[] { args[0].value };
	}
	PrintWriter out = null;
	final File f = DataTypeFile.clearFileIfMode(v);
	if (!f.exists()) {
	    new FileException(f.getName(), null, EnumFileException.DOESNT_EXIST);
	}
	if (f.isDirectory()) {
	    new FileException(f.getName(), null, EnumFileException.IS_A_DIR);
	}
	try {
	    out = new PrintWriter(new FileOutputStream(f, true));
	    for (final String element : toWrite) {
		out.print(getPrintPrefix() + element);
	    }
	    hasPrinted = true;
	    out.close();
	} catch (final Exception e) {
	    new FileException(v.dType.string(v), e);
	}

	return null;
    }

}
