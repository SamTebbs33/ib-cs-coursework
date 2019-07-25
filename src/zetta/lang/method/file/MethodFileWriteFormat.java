package zetta.lang.method.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import zetta.Zetta;
import zetta.exception.EnumFileException;
import zetta.exception.FileException;
import zetta.exception.UnexpectedTokenException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;
import zetta.lang.datatype.MappedDataType;
import zetta.lang.util.FileFormat;
import zetta.token.TokenMethodCall;
import zetta.token.TokenNumber;
import zetta.token.TokenString;
import zetta.token.TokenVariable;

/**
 * 
 * @author samueltebbs, 09:54:08 - 14 Sep 2014
 */
public class MethodFileWriteFormat extends MethodFileClear {

    public MethodFileWriteFormat(final String name) {
	super(name);
    }

    @Override
    public String[][] docArguments() {
	return new String[][] { { Zetta.dtString.name, Zetta.dtDictionary.name } };
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Writes the dictionary contents to the file according to the file format" };
    }

    @Override
    public String[] docReturnTypes() {
	return new String[] { Zetta.dtNull.name };
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
    public DataType getReturnType() {
	return Zetta.dtNull;
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable v = Zetta.getVariable((TokenVariable) object);
	final File file = DataTypeFile.getFile(v);
	PrintWriter pw = null;
	final int mode = (int) v.getIndex(DataTypeFile.INDEX_MODE);
	if (mode == DataTypeFile.MODE_APPEND) {
	    try {
		pw = new PrintWriter(new FileOutputStream(file, true));
	    } catch (final FileNotFoundException e) {
		new FileException(file, e, EnumFileException.DOESNT_EXIST);
	    }
	} else {
	    try {
		pw = new PrintWriter(new FileOutputStream(file, true));
	    } catch (final FileNotFoundException e) {
		new FileException(file, e, EnumFileException.DOESNT_EXIST);
	    }
	}
	final Variable dictionary = Zetta.getVariableOfType(args[1],
		Zetta.dtDictionary);
	final String formatStr = Token.getString(args[0]);
	final Object[] keys = ((MappedDataType) dictionary.dType)
		.getAllMappedKeys(dictionary);
	final Object[] values = ((MappedDataType) dictionary.dType)
		.getAllMappedValues(dictionary);
	final HashMap<String, String> map = new HashMap<String, String>();
	for (int c = 1; c < Math.min(keys.length, values.length); c++) {
	    map.put(keys[c].toString(), values[c].toString());
	}
	FileFormat.getFormat(formatStr, true).write(map, mode,
		file.getAbsolutePath(), pw);
	return null;
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	if (checkLength(args, true, 2)) {
	    if ((args[0] instanceof TokenVariable)
		    || (args[0] instanceof TokenNumber)
		    || (args[0] instanceof TokenMethodCall)
		    || (args[0] instanceof TokenString)) {
		if ((args[1] instanceof TokenVariable)
			|| (args[1] instanceof TokenMethodCall)) return true;
		else {
		    new UnexpectedTokenException(args[1], Zetta.tokenVariable,
			    Zetta.tokenMethodCall);
		}
	    } else {
		new UnexpectedTokenException(args[0], Zetta.tokenVariable,
			Zetta.tokenMethodCall, Zetta.tokenString,
			Zetta.tokenNumber);
	    }
	}
	return false;
    }

}
